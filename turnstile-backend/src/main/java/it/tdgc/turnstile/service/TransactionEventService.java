package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.model.Transaction;
import it.tdgc.turnstile.model.TransactionEvent;
import it.tdgc.turnstile.repository.TransactionEventRepository;
import it.tdgc.turnstile.repository.TransactionRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
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

    public TransactionEventService(TransactionEventRepository transactionEventRepository, MapperInterface mapperInterface) {
        this.transactionEventRepository = transactionEventRepository;
        this.mapperInterface = mapperInterface;
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
    public TransactionEventDTO insertTransactionEvent(TransactionEvent transactionEvent) {
        TransactionEvent savedTransactionEvent = transactionEventRepository.save(transactionEvent);
        return mapperInterface.toTransactionEventDTO(savedTransactionEvent);
    }


}
