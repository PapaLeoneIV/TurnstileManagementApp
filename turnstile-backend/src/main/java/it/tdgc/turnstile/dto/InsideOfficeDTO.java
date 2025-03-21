package it.tdgc.turnstile.dto;

import it.tdgc.turnstile.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
public class InsideOfficeDTO {
    Integer id;
    Users user;

    public InsideOfficeDTO(Integer id , Users user) {
        this.id = id;
        this.user = user;
    }
}
