package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.dto.TransactionEventInsertDTO;
import it.tdgc.turnstile.model.Transaction;
import it.tdgc.turnstile.model.TransactionEvent;
import it.tdgc.turnstile.repository.TransactionEventRepository;
import it.tdgc.turnstile.repository.TransactionRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionEventService {
    final private TransactionEventRepository transactionEventRepository;
    private final MapperInterface mapperInterface;
    private final TransactionRepository transactionRepository;

    public TransactionEventService(TransactionEventRepository transactionEventRepository, MapperInterface mapperInterface, TransactionRepository transactionRepository) {
        this.transactionEventRepository = transactionEventRepository;
        this.mapperInterface = mapperInterface;
        this.transactionRepository = transactionRepository;
    }


    @Transactional
    public TransactionEvent getLastTransactionEvent(Integer id) {
        return transactionEventRepository.findLastTransactionEventOfSpecTransaction(id);
    }


    @Transactional
    public TransactionEvent createTransactionEvent(Transaction transaction, String state) {
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setState(state);
        transactionEvent.setCreated_at(LocalDateTime.now());
        transactionEvent.setTransaction(transaction);
        return transactionEventRepository.save(transactionEvent);
    }
    @Transactional
    public ResponseEntity<ApiResponse<TransactionEventDTO>> insertTransactionEvent(TransactionEventInsertDTO transactionEvent) {
        if(transactionEvent.getTransaction_id() == null ||  transactionEvent.getTransaction_id() <= 0) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "transaction_id cannot be null or less then 0!", null);
        }
        if(transactionEvent.getState() == null ||
                (!"BADGE_PASSED".equals(transactionEvent.getState())
                && !"VALIDATING".equals(transactionEvent.getState())
                && !"OPEN_GATE".equals(transactionEvent.getState())
                && !"PASSING_THROUGH".equals(transactionEvent.getState())
                && !"CLOSING_GATE".equals(transactionEvent.getState())
                        && !"COMPLETED".equals(transactionEvent.getState()))){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "invalid transaction state", null);
        }
        if(transactionEvent.getCreated_at() == null) {
            transactionEvent.setCreated_at(LocalDateTime.now());
        }
        if(transactionRepository.findById(transactionEvent.getTransaction_id()).isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "transaction to be associated not found", null);
        }
        TransactionEvent newTe = new TransactionEvent();
        newTe.setState(transactionEvent.getState());
        newTe.setCreated_at(transactionEvent.getCreated_at());
        newTe.setTransaction(transactionRepository.getById((transactionEvent.getTransaction_id())));


        TransactionEvent savedTransactionEvent = transactionEventRepository.save(newTe);
        TransactionEventDTO teDTO = mapperInterface.toTransactionEventDTO(savedTransactionEvent);
        return responseBuilder.buildResponse(HttpStatus.OK,"OK", teDTO);
    }

    public ResponseEntity<ApiResponse<TransactionEventDTO>> searchById(Integer id) {
        if(id < 0){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<Transaction> t = transactionRepository.findById(id);
        if(t.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "The ID is not present in the DB!", null);
        }
        Optional<TransactionEvent> te = transactionEventRepository.findById(id);
        if(te.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "The ID is not present in the DB!", null);
        }
        TransactionEventDTO teDTO = mapperInterface.toTransactionEventDTO(te.get());
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", teDTO);
    }

    public ResponseEntity<ApiResponse<TransactionEventDTO>> deleteTransactionEventById(Integer id) {
        if(id < 0 || id == null){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<TransactionEvent> t = transactionEventRepository.findById(id);
        if (t .isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "TransactionEvent ID not found", null);
        }
        transactionRepository.deleteById(id);
        TransactionEventDTO tDTO = mapperInterface.toTransactionEventDTO(t .get());

        return responseBuilder.buildResponse(HttpStatus.OK, "TransactionEvent successfully deleted", tDTO);
    }
}
