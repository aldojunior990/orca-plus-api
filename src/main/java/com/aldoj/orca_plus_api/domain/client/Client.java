package com.aldoj.orca_plus_api.domain.client;


import com.aldoj.orca_plus_api.domain.products.PostProductDTO;
import com.aldoj.orca_plus_api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "clients")
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String address;

    @Column()
    private String contact;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Client(PostClientDTO data, User user) {
        this.name = data.name();
        this.address = data.address();
        this.contact = data.contact();
        this.user = user;
    }
}

