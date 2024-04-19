package com.aldoj.orca_plus_api.domain.budgets;


import com.aldoj.orca_plus_api.domain.client.Client;
import com.aldoj.orca_plus_api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "budgets")
@Table(name = "budgets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Budget(Client client, User user) {
        this.date = LocalDate.now().toString();
        this.client = client;
        this.user = user;
    }
}
