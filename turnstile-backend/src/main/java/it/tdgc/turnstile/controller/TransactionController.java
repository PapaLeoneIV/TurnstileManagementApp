package it.tdgc.turnstile.controller;


import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.model.Transaction;
import it.tdgc.turnstile.service.TransactionService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Transactional
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }




//    @GetMapping("/company/name/{name}")
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> findByCompanyName(@PathVariable("name") String name) {
//        return transactionService.findByCompanyName(name);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<TransactionDTO>> getTransactionWithId(@PathVariable("id") Integer id){
//       return transactionService.getTransactionWithId(id);
//    }
//
//    @GetMapping("/user/id/{id}")
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithUserId(@PathVariable("id")  Integer id){
//        return transactionService.getTransactionWithUserId(id);
//    }
//
//    @GetMapping("/company/id/{id}")
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithCompanyId(@PathVariable("id")  Integer id){
//        return transactionService.getTransactionWithCompanyId(id);
//    }
//
//    @GetMapping("/turnstile/id/{id}")
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithTurnstileId(@PathVariable("id")  Integer id){
//        return transactionService.getTransactionWithTurnstileId(id);
//    }
//
//    @GetMapping("/date/{YYYY-MM-DD}")
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithDate(@PathVariable("YYYY-MM-DD") LocalDate date){
//        return transactionService.getTransactionWithDate(date);
//    }
//
//    @GetMapping("/company/id/{dateStart}{dateEnd}")
//    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionWithDateRange(@PathVariable("dateStart") LocalDate dateStart, @PathVariable("dateEnd")LocalDate dateEnd){
//        return transactionService.getTransactionWithDateRange(dateStart, dateEnd);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<ApiResponse<TransactionDTO>> updateTransaction(@RequestBody Transaction transaction){
//        return transactionService.updateTransaction(transaction);
//    }
//
//    @PostMapping("/insert")
//    public ResponseEntity<ApiResponse<TransactionDTO>> insertTransaction(@RequestBody Transaction transaction){
//        return transactionService.insertTransaction(transaction);
//    }

}
