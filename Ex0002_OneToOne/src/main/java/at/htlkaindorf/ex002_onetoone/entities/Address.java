package at.htlkaindorf.ex002_onetoone.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String street;
    private String houseNumber;
    private String zipCode;
    private String town;

    @OneToOne(mappedBy = "address")
    @ToString.Exclude
    private Customer customer;
}
