package it.tdgc.turnstile.dto;

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
public class UserDTO {
    private String usertype;
    private String name;
    private String surname;
    private String email;
    private RoleDTO role;
    private CompanyDTO company;
    private BadgeDTO badge;
}
