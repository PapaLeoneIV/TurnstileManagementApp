package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.BadgeDTO;
import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
import it.tdgc.turnstile.model.Badge;
import it.tdgc.turnstile.service.BadgeService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/badge")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<BadgeDTO>> getBadgeById(@PathVariable("id") Integer id) {
        return badgeService.getBadgeById(id);
    }

    @GetMapping("/rfid/{rfid}")
    public ResponseEntity<ApiResponse<BadgeDTO>> getBadgeByRfid(@PathVariable("rfid") String rfid) {
        return badgeService.getBadgeByRfid(rfid);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<BadgeDTO>> deleteBadgeById(@PathVariable Integer id) {
        return badgeService.deleteBadgeById(id);
    }

    @DeleteMapping("/delete/rfid/{rfid}")
    public ResponseEntity<ApiResponse<BadgeDTO>> deleteBadgeByRfid(@PathVariable String rfid) {
        return badgeService.deleteBadgeByRfid(rfid);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<BadgeDTO>> updateBadge(@RequestBody Badge badge) {
        return badgeService.updateBadge(badge);
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<BadgeDTO>> insertBadge(@RequestBody BadgeDTO badge) throws BadgeAlreadyExistsException {
        return badgeService.insertBadge(badge);
    }
}
