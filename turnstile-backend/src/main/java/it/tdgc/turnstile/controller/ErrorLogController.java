package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.ErrorLogDTO;
import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
import it.tdgc.turnstile.model.ErrorLog;
import it.tdgc.turnstile.service.ErrorLogService;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/error_log")
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    public ErrorLogController(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }
    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<ErrorLogDTO>> searchTransactionById(@PathVariable("id") Integer id) {
        return errorLogService.searchById(id);
    }
    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<ErrorLogDTO>> insertBadge(@RequestBody ErrorLogDTO el) throws BadgeAlreadyExistsException {
        return errorLogService.insert(el);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<ErrorLogDTO>> deleteErrorLogById(@PathVariable Integer id) {
        return errorLogService.deleteErrorLogById(id);
    }
}
