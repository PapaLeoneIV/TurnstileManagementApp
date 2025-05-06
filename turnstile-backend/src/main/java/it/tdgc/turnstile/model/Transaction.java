package it.tdgc.turnstile.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.keycloak.models.AbstractKeycloakTransaction;

@Entity
@Table(name="transactions", schema="public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_id_seq")
    @SequenceGenerator(name = "transactions_id_seq", sequenceName = "transactions_id_seq", allocationSize = 1)
    @Getter private Integer id;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "time", nullable = false)
     private LocalTime time;

    @Column(name = "current_state", nullable = false, length=50)
     private String current_state;



    @ManyToOne
    @JoinColumn(name = "turnstile_id")
    private Turnstile turnstile;

    @ManyToOne

    @JoinColumn(name = "user_id")
     private Users user;


}