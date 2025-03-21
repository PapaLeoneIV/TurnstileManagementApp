package it.tdgc.turnstile.dto;

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
public class TimeHoursFilterDTO {
    LocalDate day;
    LocalTime start;
    LocalTime end;
}
