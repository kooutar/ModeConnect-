package com.example.ModeConnect.model;


import com.example.ModeConnect.Enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.spi.ToolProvider;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = true)
    private String profilePictureUrl;

    // Méthode abstraite à implémenter dans chaque sous-classe
    public abstract String getRole();
}
