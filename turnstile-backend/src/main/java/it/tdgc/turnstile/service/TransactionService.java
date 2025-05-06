package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.InsideOfficeDTO;
import it.tdgc.turnstile.dto.RoleDTO;
import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.dto.TransactionInsertDTO;
import it.tdgc.turnstile.model.*;
import it.tdgc.turnstile.repository.TransactionRepository;
import it.tdgc.turnstile.repository.TurnstileRepository;
import it.tdgc.turnstile.repository.UsersRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    final private TransactionRepository transactionRepository;
    final private MapperInterface mapperInterface;
    private final TurnstileRepository turnstileRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, MapperInterface mapperInterface, TurnstileRepository turnstileRepository, UsersRepository usersRepository) {
        this.transactionRepository = transactionRepository;
        this.mapperInterface = mapperInterface;
        this.turnstileRepository = turnstileRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<TransactionDTO>> insertTransaction(TransactionInsertDTO transaction) {
        if(transaction.getDate() == null || transaction.getTime() == null){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Date/Time cannot be null/empty!", null);
        }
        if(turnstileRepository.findById(transaction.getTurnstile_id()).isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Turnstile not found!", null);
        }
        if(usersRepository.findById(transaction.getUser_id()).isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "User not found!", null);
        }
        if(transaction.getCurrent_state() == null){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Current state cannot be null/empty!", null);
        }

        Transaction t = new Transaction();
        t.setDate(transaction.getDate());
        t.setTime(transaction.getTime());
        t.setCurrent_state(transaction.getCurrent_state());
        t.setUser(usersRepository.findById(transaction.getUser_id()).get());
        t.setTurnstile(turnstileRepository.findById(transaction.getTurnstile_id()).get());

        TransactionDTO tDTO = mapperInterface.toTransactionDto(transactionRepository.save(t));

        return responseBuilder.buildResponse(HttpStatus.OK, "OK", tDTO);
    }


    @Transactional
    public TransactionDTO getLastTransaction(){
        List<Transaction> t = transactionRepository.findAll();
        if(t.isEmpty()){
            return null;
        }
        return mapperInterface.toTransactionDto(t.getLast());
    }


    @Transactional
    public List<TransactionDTO>getTransactionWithDate(LocalDate date) {
        List<Transaction> transaction = transactionRepository.findWithDate(date);
        if(transaction.isEmpty()) {
            return null;
        }

        return transaction.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();
    }

    public List<TransactionDTO> getTransactionWithDateRange(LocalDate dateStart, LocalDate dateEnd) {
        List<Transaction> transactions = transactionRepository.findWithDateRange(dateStart, dateEnd);
        if(transactions.isEmpty()) {
            return null;
        }

        return transactions.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();
    }


    public List<TransactionDTO> getTransactionWithinHour(LocalDate day, LocalTime hour){

        //brings the hour in range hour:00m --> hour:59m
        LocalTime start = hour.withMinute(0).withSecond(0).withNano(0);
        LocalTime end = hour.withMinute(59).withSecond(59).withNano(999_999_999);
        List<Transaction> transactions = transactionRepository.findWithHourRange(day, start, end);
        if(transactions.isEmpty()) {
            return null;
        }
        return transactions.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();

    }

    public List<TransactionDTO> getTransactionWithHoursRange(LocalDate day, LocalTime start, LocalTime end){

        //brings the hour in range hour:00m --> hour:59m
        List<Transaction> transactions = transactionRepository.findWithHourRange(day, start, end);
        if(transactions.isEmpty()) {
            return null;
        }
        return transactions.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();

    }

    public List<TransactionDTO> getTransactionWithRole(LocalDate day, String role){
        List<Transaction> transactions = transactionRepository.getTransactionByRole(day, role);
        if(transactions.isEmpty())
            return null;
        return transactions.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();
    }


    public List<TransactionDTO> getTransactionWithCompanyName(LocalDate day, String companyName){
        List<Transaction> transactions = transactionRepository.getTransactionByCompany(day, companyName);
        if(transactions == null || transactions.isEmpty())
            return null;
        return transactions.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();

    }

    public List<TransactionDTO> getTransactionWithUserId(LocalDate day, Integer userId){
        List<Transaction> transactions = transactionRepository.getTransactionWithUserId(day, userId);
        if(transactions == null || transactions.isEmpty())
            return null;
        return transactions.stream()
                .map(mapperInterface::toTransactionDto)
                .toList();
    }
    @Transactional
    public Transaction updateTransaction(Integer transactionId, String newState) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(transactionId);
        if(existingTransaction.isEmpty()){
            return null;
        }
        Transaction t = transactionRepository.findById(transactionId).orElse(null);
        if(t == null){
            return null;
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        t.setDate(date);
        t.setTime(time);
        t.setCurrent_state(newState);

        return transactionRepository.save(t);
    }

   @Transactional
   public Transaction createTransaction(Users user, Turnstile turnstile) {
       Transaction transaction = new Transaction();

       transaction.setCurrent_state("WAITING");
       transaction.setDate(LocalDate.now());
       transaction.setTime(LocalTime.now());
       transaction.setTurnstile(turnstile);
       transaction.setUser(user);

       return transactionRepository.save(transaction);
   }


    public ResponseEntity<ApiResponse<TransactionDTO>> searchById(Integer id) {
        if(id < 0){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<Transaction> t = transactionRepository.findById(id);
        if(t.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "The ID is not present in the DB!", null);
        }
        TransactionDTO IODTO = mapperInterface.toTransactionDto(t.get());
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", IODTO);
    }

    public ResponseEntity<ApiResponse<TransactionDTO>> deleteTransactionById(Integer id) {
        if(id < 0 || id == null){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<Transaction> t = transactionRepository.findById(id);
        if (t .isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Transaction ID not found", null);
        }
        transactionRepository.deleteById(id);
        TransactionDTO tDTO = mapperInterface.toTransactionDto(t .get());

        return responseBuilder.buildResponse(HttpStatus.OK, "Transaction successfully deleted", tDTO);
    }
}
