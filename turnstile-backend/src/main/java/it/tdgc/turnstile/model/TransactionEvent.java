package it.tdgc.turnstile.model;

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
@Table(name = "transaction_event", schema="public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_event_id_seq")
    @SequenceGenerator(name = "transaction_event_id_seq", sequenceName = "transaction_event_id_seq", allocationSize = 1)
    @Getter private Integer id;

    
    @Column(name = "state", nullable = false)
     private String state;
    
    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
     private Transaction transaction;

}