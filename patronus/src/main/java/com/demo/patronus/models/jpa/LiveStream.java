package com.demo.patronus.models.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "streams")
@ToString(exclude = {"user", "likes"})
public class LiveStream {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id")
    private UUID id;
    private String caption;
    private String description;
    private String thumbnailUrl;
    private boolean archived;
    private boolean live=true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "stream")
    private List<Like> likes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    public int getLikeCount() {
        return likes != null ? likes.size() : 0;
    }
}
