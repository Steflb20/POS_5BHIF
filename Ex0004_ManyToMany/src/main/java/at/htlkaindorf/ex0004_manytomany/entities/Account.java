package at.htlkaindorf.ex0004_manytomany.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountNr;
    private double value = 0.0;

    @ManyToMany(mappedBy = "accounts")
    private List<Customer> customers;
}
