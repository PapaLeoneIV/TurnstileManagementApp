package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.InsideOfficeDTO;
import it.tdgc.turnstile.dto.InsideOfficeInsertDTO;
import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
import it.tdgc.turnstile.model.InsideOffice;
import it.tdgc.turnstile.service.InsideOfficeService;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/insideOffice")
public class InsideOfficeController {

    final private InsideOfficeService insideOfficeService;

    @Autowired
    public InsideOfficeController(InsideOfficeService insideOfficeService){
        this.insideOfficeService = insideOfficeService;
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<InsideOfficeDTO>> insertBadge(@RequestBody InsideOfficeInsertDTO io) throws BadgeAlreadyExistsException {
        return insideOfficeService.insertInsideOffice(io);
    }
}
