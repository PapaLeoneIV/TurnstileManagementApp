package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.RoleDTO;
import it.tdgc.turnstile.dto.TurnstileDTO;
import it.tdgc.turnstile.dto.UserDTO;
import it.tdgc.turnstile.model.Role;
import it.tdgc.turnstile.model.Turnstile;
import it.tdgc.turnstile.service.TurnstileService;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("id/{id}")
    public ResponseEntity<ApiResponse<TurnstileDTO>> getTurnstileWithId(@PathVariable Integer id){
        Turnstile t = turnstileService.getTurnstileWithId(id);
        TurnstileDTO tDTO = mapperInterface.toTurnstileDTO(t);
        return buildResponse(HttpStatus.OK, "Turnstile with id " + id + " found", tDTO);
    }


    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<TurnstileDTO>> deleteTurnstileById(@PathVariable Integer id) {
        return turnstileService.deleteTurnstileById(id);
    }
//
//    @PutMapping("/update")
//    public Turnstile updateTurnstile(@RequestBody Turnstile turnstile) {
//        return turnstileService.updateTurnstile(turnstile.getId());
//    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<TurnstileDTO>> insertTurnstile(@RequestBody Turnstile turnstile) {
        return turnstileService.insertTurnstile(turnstile);
    }

    //TODO move into an interface
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
