package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.TurnstileDTO;
import it.tdgc.turnstile.dto.TurnstileInsertDTO;
import it.tdgc.turnstile.model.Turnstile;
import it.tdgc.turnstile.repository.TurnstileRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class TurnstileService {
    private final TurnstileRepository  turnstileRepository;
    private final MapperInterface mapperInterface;


    public TurnstileService(TurnstileRepository turnstileRepository, MapperInterface mapperInterface) {
        this.turnstileRepository = turnstileRepository;
        this.mapperInterface = mapperInterface;
    }

    @Transactional
    public Turnstile getTurnstileWithId(Integer id){
        return turnstileRepository.findById(id).orElseGet(()->null);
    }

    @Transactional
    public ResponseEntity<ApiResponse<TurnstileDTO>> deleteTurnstileById(Integer id) {
        Optional<Turnstile> turnstile = turnstileRepository.findById(id);
        if (turnstile.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Turnstile ID not found", null);
        }
        turnstileRepository.deleteById(id);
        TurnstileDTO turnstileDTO = mapperInterface.toTurnstileDTO(turnstile.get());

        return responseBuilder.buildResponse(HttpStatus.OK, "Turnstile successfully deleted", turnstileDTO);
    }

    @Transactional
    public Turnstile updateTurnstile(Integer id, boolean available) {
        Turnstile existingTurnstile = turnstileRepository.findById(id).orElseGet(null);
        if(existingTurnstile == null) {
            return null;
        }
        existingTurnstile.setAvailable(available);
        return turnstileRepository.save(existingTurnstile);
    }

    @Transactional
    public ResponseEntity<ApiResponse<TurnstileDTO>> insertTurnstile(TurnstileInsertDTO turnstile) {

        Turnstile newTurnstile = new Turnstile();
        newTurnstile.setAvailable(turnstile.isAvailable());

        Turnstile savedTurnstile = turnstileRepository.save(newTurnstile);
        TurnstileDTO turnstileDTO = mapperInterface.toTurnstileDTO(savedTurnstile);

        return responseBuilder.buildResponse(HttpStatus.OK, "Turnstile created successfully", turnstileDTO);
    }

}
