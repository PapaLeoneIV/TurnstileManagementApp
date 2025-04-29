package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.BadgeDTO;
import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
import it.tdgc.turnstile.model.Badge;
import it.tdgc.turnstile.repository.BadgeRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final MapperInterface mapperInterface;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository, MapperInterface mapperInterface) {
        this.badgeRepository = badgeRepository;
        this.mapperInterface = mapperInterface;
    }

    @Transactional
    public ResponseEntity<ApiResponse<BadgeDTO>> getBadgeById(Integer id) {
        Optional<Badge> badge = badgeRepository.findById(id);
        if (badge.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Badge ID not found", null, new Date(), null));
        }

        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(badge.get());

        return ResponseEntity.ok(new ApiResponse<>("200", "Badge found", badgeDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<BadgeDTO>> getBadgeByRfid(String rfid) {
        Optional<Badge> badge = badgeRepository.findByRfid(rfid);
        if (badge.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Badge RFID not found", null, new Date(), null));
        }

        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(badge.get());


        return ResponseEntity.ok(new ApiResponse<>("200", "Badge found", badgeDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<BadgeDTO>> deleteBadgeById(Integer id) {
        Optional<Badge> badge = badgeRepository.findById(id);
        if (badge.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Badge ID not found", null, new Date(), null));
        }
        badgeRepository.deleteById(id);

        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(badge.get());


        return ResponseEntity.ok(new ApiResponse<>("200", "Badge successfully deleted", badgeDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<BadgeDTO>> deleteBadgeByRfid(String rfid) {
        Optional<Badge> badge = badgeRepository.findByRfid(rfid);
        if (badge.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Badge RFID not found", null, new Date(), null));
        }
        badgeRepository.deleteById(badge.get().getId());

        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(badge.get());

        return ResponseEntity.ok(new ApiResponse<>("200", "Badge successfully deleted", badgeDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<BadgeDTO>> updateBadge(Badge badge) {
        Optional<Badge> badgeToUpdate = badgeRepository.findById(badge.getId());
        if (badgeToUpdate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Badge ID not found", null, new Date(), null));
        }

        Badge updatedBadge = badgeToUpdate.get();
        updatedBadge.setRfid(badge.getRfid());
        badgeRepository.save(updatedBadge);

        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(badgeToUpdate.get());


        return ResponseEntity.ok(new ApiResponse<>("204", "Badge updated successfully", badgeDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<BadgeDTO>> insertBadge(BadgeDTO badge) {
        if(badge.getRfid().isEmpty() || badge.getRfid().isBlank()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "The rfid cannot be empty", null);
        }
        if(badge.getAllowed_enter_time().isAfter(badge.getAllowed_exit_time())) {
            return responseBuilder.buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "The entered time cannot be after the exit", null);
        }
        if (badge.getExpiry().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            return responseBuilder.buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "The expiry time cannot be after the exit", null);
        }

        Badge newBadge = new Badge();
        newBadge.setRfid(badge.getRfid());
        newBadge.setAllowed_enter_time(badge.getAllowed_enter_time());
        newBadge.setExpiry(badge.getExpiry());
        newBadge.setAllowed_exit_time(badge.getAllowed_exit_time());

        Badge savedBadge = badgeRepository.save(newBadge);
        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(savedBadge);

        return responseBuilder.buildResponse(HttpStatus.OK, "Badge created successfully", badgeDTO);
    }
}
