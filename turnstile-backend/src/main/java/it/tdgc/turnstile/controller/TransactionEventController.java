package it.tdgc.turnstile.controller;


import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.dto.TransactionEventInsertDTO;
import it.tdgc.turnstile.model.TransactionEvent;
import it.tdgc.turnstile.service.TransactionEventService;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
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
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", tDTO);
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<TransactionEventDTO>> insert(@RequestBody TransactionEventInsertDTO te) {
        return transactionEventService.insertTransactionEvent(te);
    }


}
