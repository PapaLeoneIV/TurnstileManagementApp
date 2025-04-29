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
public class ErrorLogDTO {
    LocalDateTime created_at;
    Integer turnstile_id;
    Integer user_id;
    String error_message;
}
