package at.htlkaindorf.ex002_onetoone.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String surname;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "address")
    private Address address;
}
