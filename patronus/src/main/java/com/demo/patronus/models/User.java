package com.demo.patronus.models;

import com.demo.patronus.annotation.Password;
import com.demo.patronus.security.oauth.OAuth2Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
    private String bio;
    private String name;
    @Column(name = "email_confirmed")
    private boolean emailConfirmed=false;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    private String providerId;

    @JsonIgnore
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> following;

    @JsonIgnore
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "blocker", fetch = FetchType.LAZY)
    private List<Block> blocking;

    @JsonIgnore
    @OneToMany(mappedBy = "blocked", fetch = FetchType.LAZY)
    private List<Block> blockedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<LiveStream> liveStream;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
