package it.tdgc.turnstile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "inside_office_id_seq")
    @SequenceGenerator(name = "inside_office_id_seq", sequenceName = "inside_office_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private Users user;

    public InsideOffice(Users user) {
        this.user = user;
    }

}