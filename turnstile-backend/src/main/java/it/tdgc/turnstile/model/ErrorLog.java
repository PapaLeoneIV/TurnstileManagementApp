package it.tdgc.turnstile.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "error_log", schema = "public")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "error_log_id_seq")
    @SequenceGenerator(name = "error_log_id_seq", sequenceName = "error_log_id_seq", allocationSize = 1)
    @Getter private Integer id;

    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_at;

    @Column(name = "error_message", nullable = false, length=255)
    private String error_message;

    @ManyToOne
    @JoinColumn(name = "turnstile_id")
    private Turnstile turnstile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}