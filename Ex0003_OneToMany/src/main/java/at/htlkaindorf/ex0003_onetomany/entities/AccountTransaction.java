package at.htlkaindorf.ex0003_onetomany.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double value;
    private LocalDate date;
    private String text;

    @ManyToOne()
    @JoinColumn(name = "account")
    private Account account;
}
