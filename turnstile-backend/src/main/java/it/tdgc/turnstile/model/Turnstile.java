package it.tdgc.turnstile.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table  (name = "turnstile", schema = "public")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turnstile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turnstile_id_seq")
    @SequenceGenerator(name = "turnstile_id_seq", sequenceName = "turnstile_id_seq", allocationSize = 1)
    private Integer id;
    
    @Column(name = "available", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean available;
}