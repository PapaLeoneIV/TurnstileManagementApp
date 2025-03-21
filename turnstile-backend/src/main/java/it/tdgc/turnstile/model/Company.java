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
@Table(name = "company", schema="public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company{

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "badge_id_seq")
    @SequenceGenerator(name = "badge_id_seq", sequenceName = "badge_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", nullable=false, length=100)
    private String name;

    @Column(name = "address", nullable=false, length=100)
    private String address;


}