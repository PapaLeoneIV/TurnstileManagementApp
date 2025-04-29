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
public class TransactionInsertDTO {
    private LocalDate date;
    private LocalTime time;
    private String current_state;
    private Integer user_id;
    private Integer turnstile_id;
}
