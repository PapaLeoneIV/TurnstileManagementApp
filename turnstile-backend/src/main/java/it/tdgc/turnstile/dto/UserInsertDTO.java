package it.tdgc.turnstile.dto;

import it.tdgc.turnstile.model.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDTO {
    private String usertype;
    private String name;
    private String surname;
    private String email;
    private Integer role_id;
    private Integer company_id;
    private String allowed_enter_time;
    private String allowed_exit_time;
    private String expiry;
}
