package com.demo.patronus.models;

import com.demo.patronus.annotation.Password;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    @Column(name = "email_confirmed")
    private boolean emailConfirmed=false;

    @Password
    private String password;
    private String imageUrl;
    private String bio;
    private String name;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> following;

    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followedBy;

    @OneToMany(mappedBy = "blocker", fetch = FetchType.LAZY)
    private List<Block> blocking;

    @OneToMany(mappedBy = "blocked", fetch = FetchType.LAZY)
    private List<Block> blockedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<LiveStream> liveStream;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                '}';
    }



}
