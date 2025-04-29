package it.tdgc.turnstile.dto;


import it.tdgc.turnstile.model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
public class InsideOfficeInsertDTO {
    Integer user_id;
}
