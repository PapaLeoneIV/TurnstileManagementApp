package it.tdgc.turnstile.controller;


import it.tdgc.turnstile.dto.TransactionDTO;
import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.model.TransactionEvent;
import it.tdgc.turnstile.service.TransactionEventService;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Transactional
@RequestMapping(path = "/transaction_event")
public class TransactionEventController {
    final private TransactionEventService transactionEventService;
    private final MapperInterface mapperInterface;

    public TransactionEventController(TransactionEventService transactionEventService, MapperInterface mapperInterface) {
        this.transactionEventService = transactionEventService;
        this.mapperInterface = mapperInterface;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TransactionEventDTO>> getLastTransactionEvent(@RequestParam Integer id) {
        TransactionEvent t = transactionEventService.getLastTransactionEvent(id);
        TransactionEventDTO tDTO = mapperInterface.toTransactionEventDTO(t);
        return buildResponse(HttpStatus.OK, "OK", tDTO);
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
