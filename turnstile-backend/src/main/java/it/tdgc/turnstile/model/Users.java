package it.tdgc.turnstile.model;

import it.tdgc.turnstile.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;


@Entity
@Table (name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("state = 'EXIST'")
@SQLDelete(sql = "UPDATE users SET state = 'DELETED' WHERE id = ?")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "usertype", nullable = false, length = 100)
    private String usertype;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    @Column(name = "state", columnDefinition = "STATE VARCHAR(25) DEFAULT 'EXIST'")
    private String state = "EXIST";

    @OneToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;



}