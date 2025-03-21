package it.tdgc.turnstile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class TimeDayFilterDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
