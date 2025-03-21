package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.model.Transaction;
import it.tdgc.turnstile.model.TransactionEvent;
import it.tdgc.turnstile.model.Turnstile;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.TransactionRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    final private TransactionRepository transactionRepository;
    final private MapperInterface mapperInterface;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, MapperInterface mapperInterface) {
        this.transactionRepository = transactionRepository;
        this.mapperInterface = mapperInterface;
    }




    @Transactional
    public TransactionDTO getLastTransaction(){
        List<Transaction> t = transactionRepository.findAll();
        if(t.isEmpty()){
            return null;
        }
        return mapperInterface.toTransactionDto(t.get(t.size()-1));
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

//    @Transactional
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> findByCompanyName(String companyName) {
//        List<Transaction> transactions =  transactionRepository.findByCompanyName(companyName);
//        if(transactions.isEmpty()) {
//            return buildResponse(HttpStatus.NOT_FOUND, "Transaction with company name" + companyName + "not found", null);
//        }
//
//        List<TransactionDTO> transactionDTO = transactions.stream()
//                .map(mapperInterface::toTransactionDto)
//                .toList();
//        return buildResponse(HttpStatus.OK, "OK", transactionDTO);
//
//    }
//
//    @Transactional
//    public ResponseEntity<ApiResponse<TransactionDTO>> getTransactionWithId(Integer id) {
//        Optional<Transaction> transaction = transactionRepository.findById(id);
//        if(transaction.isEmpty()) {
//            return buildResponse(HttpStatus.NOT_FOUND, "Transaction id not found", null);
//        }
//        TransactionDTO transactionDTO = mapperInterface.toTransactionDto(transaction.get());
//        return buildResponse(HttpStatus.OK, "OK", transactionDTO);
//
//    }
//
//    @Transactional
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithUserId(Integer userId) {
//        List<Transaction> transaction = transactionRepository.findWithUserId(userId);
//        if(transaction.isEmpty()) {
//            return buildResponse(HttpStatus.NOT_FOUND, "Transaction id not found", null);
//        }
//        List<TransactionDTO> transactionDTO = transaction.stream()
//                .map(mapperInterface::toTransactionDto)
//                .toList();
//        return buildResponse(HttpStatus.OK, "OK", transactionDTO);
//    }
//
//    @Transactional
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithTurnstileId(Integer id){
//        List<Transaction> transaction = transactionRepository.findWithTurnstileId(id);
//        if(transaction.isEmpty()) {
//            return buildResponse(HttpStatus.NOT_FOUND, "Transaction id not found", null);
//        }
//
//        List<TransactionDTO> transactionDTO = transaction.stream()
//                .map(mapperInterface::toTransactionDto)
//                .toList();
//
//        return ResponseEntity.ok(new ApiResponse<>("200", "OK", transactionDTO, new Date(), null));
//
//    }

//    @Transactional
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithCompanyId(Integer id) {
//        List<Transaction> transaction = transactionRepository.findWithCompanyId(id);
//        if(transaction.isEmpty()) {
//            return buildResponse(HttpStatus.NOT_FOUND, "Transaction id not found", null);
//        }
//        List<TransactionDTO> transactionDTOs = transaction.stream()
//                .map(mapperInterface::toTransactionDto)
//                .toList();
//        return buildResponse(HttpStatus.OK, "OK", transactionDTOs);
//    }
//    @Transactional


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

        Transaction newt =  transactionRepository.save(t);

        return newt;
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

    private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(
                String.valueOf(status.value()),
                message,
                data,
                new Date(),
                null
        );
        return ResponseEntity.status(status).body(response);
    }
}
