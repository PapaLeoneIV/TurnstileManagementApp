package it.tdgc.turnstile.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDTO {
    private Integer id;
    private String rfid;
    private LocalTime allowed_enter_time;
    private LocalTime allowed_exit_time;
    private LocalDate expiry;

}
