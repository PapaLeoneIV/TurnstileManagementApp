package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.CompanyDTO;
import it.tdgc.turnstile.dto.InsideOfficeDTO;
import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.dto.TransactionInsertDTO;
import it.tdgc.turnstile.service.TransactionService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<TransactionDTO>> searchTransactionById(@PathVariable("id") Integer id) {
        return transactionService.searchById(id);
    }
   @PostMapping("/insert")
    public ResponseEntity<ApiResponse<TransactionDTO>> insertTransaction(@RequestBody  TransactionInsertDTO transaction){
        return transactionService.insertTransaction(transaction);
    }
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<TransactionDTO>> deleteTransactionById(@PathVariable Integer id) {
        return transactionService.deleteTransactionById(id);
    }
}
