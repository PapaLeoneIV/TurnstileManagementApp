package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.BadgeDTO;
import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
import it.tdgc.turnstile.model.Badge;
import it.tdgc.turnstile.repository.BadgeRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
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
    public ResponseEntity<ApiResponse<BadgeDTO>> insertBadge(Badge badge) {
        Badge savedBadge;
        try {
            savedBadge = badgeRepository.save(badge);
        } catch (DataIntegrityViolationException e) {
            throw new BadgeAlreadyExistsException("Badge with the same RFID already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the badge, probably violated uniqueness", e);
        }

        BadgeDTO badgeDTO = mapperInterface.toBadgeDTO(savedBadge);

        return ResponseEntity.ok(new ApiResponse<>("200", "Badge created successfully", badgeDTO, new Date(), null));
    }
}
