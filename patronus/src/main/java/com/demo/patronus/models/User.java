package com.demo.patronus.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    private String imageUrl;

    private String externalUserId;

    private String bio;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> following;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> followedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Block> blocking;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Block> blockedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Stream> stream;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "email_confirmed")
    private boolean emailConfirmed;
}
