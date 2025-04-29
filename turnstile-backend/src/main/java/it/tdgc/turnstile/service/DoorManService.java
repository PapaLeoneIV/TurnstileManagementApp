package it.tdgc.turnstile.service;


import it.tdgc.turnstile.dto.EnterInfoDTO;
import it.tdgc.turnstile.model.*;
import it.tdgc.turnstile.repository.*;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.responseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
public class DoorManService {
    final private TurnstileRepository turnstileRepository;
    final private TransactionEventService transactionEventService;
    final private TransactionService transactionService;
    private final BadgeRepository badgeRepository;
    private final UsersRepository usersRepository;
    private final InsideOfficeService insideOfficeService;
    private final TurnstileService turnstileService;
    private final ErrorLogService errorLogService;

    public DoorManService(
            TurnstileRepository turnstileRepository,
            TransactionEventService transactionEventService,
            TransactionService transactionService,
            BadgeRepository badgeRepository,
            UsersRepository usersRepository,
            InsideOfficeService insideOfficeService,
            TurnstileService turnstileService,
            ErrorLogService errorLogService
    )
    {
        this.turnstileRepository = turnstileRepository;
        this.transactionEventService = transactionEventService;
        this.badgeRepository = badgeRepository;
        this.usersRepository = usersRepository;
        this.insideOfficeService = insideOfficeService;
        this.transactionService = transactionService;
        this.turnstileService = turnstileService;
        this.errorLogService = errorLogService;
    }

    //TODO check expiry on badge
    public ResponseEntity<ApiResponse<String>> checkEntrance(@RequestBody EnterInfoDTO enterInfo) {
            Turnstile turnstile = turnstileRepository.findById(enterInfo.getTurnstileId()).orElse(null);
            if (turnstile == null) {
                String msg = "Turnstile ID " + enterInfo.getTurnstileId() + " is not valid";
                errorLogService.createErrorLog(msg, null, null);
                return responseBuilder.buildResponse(HttpStatus.OK, msg, msg);
            }

            if (!turnstile.isAvailable()) {
                String msg = "The turnstile selected with Id: " + enterInfo.getTurnstileId() + " is not available";
                errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
                return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
            }

            Badge badge = badgeRepository.findById(enterInfo.getBadgeId()).orElse(null);
            if (badge == null) {
                String msg = "Badge ID " + enterInfo.getBadgeId() + " not found. Please register to enter.";
                errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
                return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
            }

            Users user = usersRepository.findUsersByBadgeId(badge.getId()).orElse(null);
            if (user == null) {
                String msg = "Badge ID " + enterInfo.getBadgeId() + " not found. Please register to enter.";
                errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
                return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
            }

            if (insideOfficeService.isInsideOffice(user.getId())) {
                String msg = "The user: " + user.getName() + " " + user.getSurname() + " with badge Rfid " + user.getBadge().getRfid() + " is already inside the office!";
                errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), user);
                return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
            }
            //-----SIMULATION----//
            Transaction transaction = transactionService.createTransaction(user, turnstile);
            System.out.println("Transaction created: {}" + transaction);

            turnstileService.updateTurnstile(enterInfo.getTurnstileId(), false);
            System.out.println("Turnstile updated to unavailable");

            transactionEventService.createTransactionEvent(transaction, "BADGE PASSED");
            transactionEventService.createTransactionEvent(transaction, "VALIDATING");
            transactionEventService.createTransactionEvent(transaction, "OPEN_GATE");
            transactionEventService.createTransactionEvent(transaction, "PASSING_THROUGH");
            transactionEventService.createTransactionEvent(transaction, "CLOSING_GATE");
            transactionEventService.createTransactionEvent(transaction, "COMPLETED");

            turnstileService.updateTurnstile(enterInfo.getTurnstileId(), true);
            System.out.println("Turnstile updated to available");

            TransactionEvent lastEvent = transactionEventService.getLastTransactionEvent(transaction.getId());
            //-----SIMULATION----//

            if ("COMPLETED".equals(lastEvent.getState())) {
                transactionService.updateTransaction(transaction.getId(), lastEvent.getState());
                insideOfficeService.addUser(user);
                return responseBuilder.buildResponse(HttpStatus.OK, "OK", "You are inside the office.");
            }

        String msg = "Unexpected error occurred during transaction processing: ";
        errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(),null);
        System.out.println("Exception during transaction processing");
        return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "There has been an error", null);
    }

    //TODO check expiry on badge
    public ResponseEntity<ApiResponse<String>> checkExit(@RequestBody EnterInfoDTO enterInfo) {
        Turnstile turnstile = turnstileRepository.findById(enterInfo.getTurnstileId()).orElse( null);
        if (turnstile == null) {
            //TODO need to create error_log
            String msg = "Turnstile ID " + enterInfo.getTurnstileId() + " is not valid";
            errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
            return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
        }
        if (!turnstile.isAvailable()) {
            //TODO need to create error_log
            String msg = "The turnstile selected with Id: " + enterInfo.getTurnstileId() + " is not available";
            errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, msg, null);
        }
        Badge badge = badgeRepository.findById(enterInfo.getBadgeId()).orElse( null);
        if(badge == null){
            String msg = "Badge ID " + enterInfo.getBadgeId() + " not found. Please register to enter.";
            errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
            return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
        }
        Users user = usersRepository.findUsersByBadgeId(badge.getId()).orElse(null);
        if(user == null){
            //TODO need to create error_log
            String msg = "Badge ID " + enterInfo.getBadgeId() + " not found. Please register to enter.";
            errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), null);
            return responseBuilder.buildResponse(HttpStatus.OK, msg, null);
        }
        if(!insideOfficeService.isInsideOffice(user.getId()))
        {
            //TODO need to create error_log
            String msg = "The user : " + user.getName() + " " + user.getSurname() + " with badge Rfid " + user.getBadge().getRfid() + " is NOT inside the office!";
            errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), user);
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, msg, null);
        }


        //-----SIMULATION----//
        Transaction transaction = transactionService.createTransaction(user, turnstile);

        turnstileService.updateTurnstile(enterInfo.getTurnstileId(), false);

        TransactionEvent tE = transactionEventService.createTransactionEvent(transaction, "BADGE PASSED");
        transactionEventService.createTransactionEvent(transaction, "VALIDATING");
        transactionEventService.createTransactionEvent(transaction, "OPEN_GATE");
        transactionEventService.createTransactionEvent(transaction, "PASSING_THROUGH");
        transactionEventService.createTransactionEvent(transaction, "CLOSING_GATE");
        transactionEventService.createTransactionEvent(transaction, "COMPLETED");

        turnstileService.updateTurnstile(enterInfo.getTurnstileId(), true);

        TransactionEvent lastEvent = transactionEventService.getLastTransactionEvent(transaction.getId());
        //-----SIMULATION----//

        if("COMPLETED".equals(lastEvent.getState())){
            transactionService.updateTransaction(transaction.getId(), lastEvent.getState());
            insideOfficeService.removeUser(user);
            return responseBuilder.buildResponse(HttpStatus.OK, "OK", "You have exited the office.");
        }

        String msg = "Unexpected error occurred. Please contact the administrator.";
        errorLogService.createErrorLog(msg, enterInfo.getTurnstileId(), user);
        return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "There has been an error", null);
    }


}



