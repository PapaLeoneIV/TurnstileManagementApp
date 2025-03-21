package it.tdgc.turnstile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEventDTO {
    private String state;
    private LocalDateTime created_at;
    private TransactionDTO transaction;
}
