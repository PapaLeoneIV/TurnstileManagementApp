package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.EnterInfoDTO;
import it.tdgc.turnstile.service.DoorManService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/doorman")
public class DoorManController {

    final private DoorManService doorManService;

    public DoorManController(DoorManService doorManService){
        this.doorManService = doorManService;
    }

    @PostMapping("/enter")
    public ResponseEntity<ApiResponse<String>> enter(@RequestBody EnterInfoDTO enterInfo) {
        return doorManService.checkEntrance(enterInfo);
    }

    @PostMapping("/exit")
    public ResponseEntity<ApiResponse<String>> exit(@RequestBody EnterInfoDTO enterInfo) {
        return doorManService.checkExit(enterInfo);
    }

}
