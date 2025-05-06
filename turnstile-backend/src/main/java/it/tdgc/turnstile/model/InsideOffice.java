package it.tdgc.turnstile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="inside_office", schema="public")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsideOffice{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inside_office_id_seq")
    @SequenceGenerator(name = "inside_office_id_seq", sequenceName = "inside_office_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private Users user;

    public InsideOffice(Users user) {
        this.user = user;
    }

}