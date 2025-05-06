package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.ErrorLogDTO;
import it.tdgc.turnstile.dto.TransactionEventDTO;
import it.tdgc.turnstile.model.ErrorLog;
import it.tdgc.turnstile.model.TransactionEvent;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.ErrorLogRepository;
import it.tdgc.turnstile.repository.UsersRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class ErrorLogService {
    private final TurnstileService turnstileService;
    private final MapperInterface mapperInterface;
    private final ErrorLogRepository errorLogRepository;
    private final UsersRepository usersRepository;

    public ErrorLogService(ErrorLogRepository errorLogRepository, TurnstileService turnstileService, MapperInterface mapperInterface, UsersRepository usersRepository) {
        this.errorLogRepository = errorLogRepository;
        this.turnstileService = turnstileService;
        this.mapperInterface = mapperInterface;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void createErrorLog(String msg, Integer turnstileId, Users user){
        ErrorLog errorLog = new ErrorLog();
        errorLog.setUser(user);
        errorLog.setCreated_at(LocalDateTime.now());
        errorLog.setError_message(msg);
        if(turnstileId != null)
            errorLog.setTurnstile(turnstileService.getTurnstileWithId(turnstileId));
        else
            errorLog.setTurnstile(null);
        errorLogRepository.save(errorLog);
    }


    @Transactional
    public ResponseEntity<ApiResponse<ErrorLogDTO>> insert(ErrorLogDTO errorLog){
        if(errorLog.getError_message() == null || errorLog.getError_message().isEmpty()){
            responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "error message must be present!", null);
        }
        if(errorLog.getCreated_at() == null){
            errorLog.setCreated_at(LocalDateTime.now());
        }
        if(errorLog.getUser_id() == null || usersRepository.findById(errorLog.getUser_id()).isEmpty()){
            responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Invalid user id!", null);
        }
        if(errorLog.getTurnstile_id() == null || turnstileService.getTurnstileWithId(errorLog.getTurnstile_id()) == null){
            responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Invalid turnstile id!", null);
        }

        ErrorLog el = new ErrorLog();
        el.setError_message(errorLog.getError_message());
        el.setUser(usersRepository.findById(errorLog.getUser_id()).get());
        el.setTurnstile(turnstileService.getTurnstileWithId(errorLog.getTurnstile_id()));
        el.setCreated_at(errorLog.getCreated_at());

        ErrorLog saved =  errorLogRepository.save(el);

        ErrorLogDTO elDTO = mapperInterface.toErrorLogDTO(saved);

        return responseBuilder.buildResponse(HttpStatus.OK, "OK", elDTO);
    }

    public ResponseEntity<ApiResponse<ErrorLogDTO>> searchById(Integer id) {
    if(id < 0){
        return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
    }
    Optional<ErrorLog> el = errorLogRepository.findById(id);
    if(el.isEmpty()){
        return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Not found!", null);
    }
    ErrorLogDTO elDTO = mapperInterface.toErrorLogDTO(el.get());
    return responseBuilder.buildResponse(HttpStatus.OK, "OK", elDTO);
    }

    public ResponseEntity<ApiResponse<ErrorLogDTO>> deleteErrorLogById(Integer id) {
        if(id == null || id < 0){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<ErrorLog> el = errorLogRepository.findById(id);
        if (el .isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "ErrorLog ID not found", null);
        }
        errorLogRepository.deleteById(id);
        ErrorLogDTO tDTO = mapperInterface.toErrorLogDTO(el .get());

        return responseBuilder.buildResponse(HttpStatus.OK, "ErrorLog successfully deleted", tDTO);
    }
}


