package com.demo.patronus.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "is_email_confirmed")
    private boolean isEmailConfirmed;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "user_role", nullable = false)
//    private Role role;

    @Column(name = "is_banned")
    private boolean isBanned;
}
