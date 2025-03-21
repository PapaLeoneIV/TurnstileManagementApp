package it.tdgc.turnstile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "badge", schema="public")
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "badge_id_seq")
    @SequenceGenerator(name = "badge_id_seq", sequenceName = "badge_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "allowed_enter_time", nullable=false)
    private LocalTime allowed_enter_time;

    @Column(name = "allowed_exit_time", nullable=false)
    private LocalTime allowed_exit_time;

    @Column(name = "expiry", nullable = false)
    private LocalDate expiry;

    @Column(name = "rfid", nullable=false)
    private String rfid;

}
