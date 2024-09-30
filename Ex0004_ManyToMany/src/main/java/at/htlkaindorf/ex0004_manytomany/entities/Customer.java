package at.htlkaindorf.ex0004_manytomany.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;

    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;

    @ManyToMany
    @JoinTable(
            name = "customer_account",
            joinColumns = { @JoinColumn(name = "customer") },
            inverseJoinColumns = { @JoinColumn(name = "account")}
    )
    private List<Account> accounts;

}
