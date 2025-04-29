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
public class TransactionEventInsertDTO {
    private LocalDateTime created_at;
    private String state;
    private Integer transaction_id;
}

