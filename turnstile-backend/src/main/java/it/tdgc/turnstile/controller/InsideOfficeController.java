package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.InsideOfficeDTO;
import it.tdgc.turnstile.service.InsideOfficeService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping(path = "/insideOffice")
public class InsideOfficeController {

    final private InsideOfficeService insideOfficeService;

    @Autowired
    public InsideOfficeController(InsideOfficeService insideOfficeService){
        this.insideOfficeService = insideOfficeService;
    }


//
//    @GetMapping("total")
//    public ResponseEntity<ApiResponse<InsideOfficeDTO>> getNumberOfUsersInsideOffice(){
//        return insideOfficeService.getNumberOfUsersInsideOffice();
//    }

}
