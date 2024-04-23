package com.demo.patronus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "streams")
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
    @JsonIgnore
    private User user;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "stream")
    private List<Like> likes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public int getLikeCount() {
        return likes != null ? likes.size() : 0;
    }

    @Override
    public String toString() {
        return "LiveStream{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }


}
