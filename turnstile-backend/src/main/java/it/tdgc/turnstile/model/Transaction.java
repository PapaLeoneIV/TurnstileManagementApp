package it.tdgc.turnstile.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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