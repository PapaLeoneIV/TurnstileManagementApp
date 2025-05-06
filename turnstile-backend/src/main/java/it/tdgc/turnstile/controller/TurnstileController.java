package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.TurnstileDTO;
import it.tdgc.turnstile.dto.TurnstileInsertDTO;
import it.tdgc.turnstile.model.Turnstile;
import it.tdgc.turnstile.service.TurnstileService;
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
@RequestMapping(path = "/turnstile")
public class TurnstileController {
    private final TurnstileService turnstileService;
    private final MapperInterface mapperInterface;

    public TurnstileController(TurnstileService turnstileService, MapperInterface mapperInterface) {
        this.turnstileService = turnstileService;
        this.mapperInterface = mapperInterface;
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<TurnstileDTO>> getTurnstileWithId(@PathVariable Integer id){
        Turnstile t = turnstileService.getTurnstileWithId(id);
        TurnstileDTO tDTO = mapperInterface.toTurnstileDTO(t);
        return responseBuilder.buildResponse(HttpStatus.OK, "Turnstile with id " + id + " found", tDTO);
    }


    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<TurnstileDTO>> deleteTurnstileById(@PathVariable Integer id) {
        return turnstileService.deleteTurnstileById(id);
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<TurnstileDTO>> insertTurnstile(@RequestBody TurnstileInsertDTO turnstile) {
        return turnstileService.insertTurnstile(turnstile);
    }
}
