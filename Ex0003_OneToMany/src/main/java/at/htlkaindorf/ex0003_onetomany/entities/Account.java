package at.htlkaindorf.ex0003_onetomany.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double value;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountTransaction> transactions = new ArrayList<>();
}
