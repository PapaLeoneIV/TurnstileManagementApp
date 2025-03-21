package it.tdgc.turnstile.service;

import it.tdgc.turnstile.model.ErrorLog;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.ErrorLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ErrorLogService {
    private final TurnstileService turnstileService;
    ErrorLogRepository errorLogRepository;

    public ErrorLogService(ErrorLogRepository errorLogRepository, TurnstileService turnstileService) {
        this.errorLogRepository = errorLogRepository;
        this.turnstileService = turnstileService;
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
}


