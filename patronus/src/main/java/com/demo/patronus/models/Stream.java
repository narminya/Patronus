package com.demo.patronus.models;

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
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    private String caption;
    private String thumbnailUrl;
    private String ingressId;
    private String serverUrl;
    private String streamKey;
    //cache??
    private boolean live;
    private boolean chatEnabled;
    private boolean chatDelayed;
    private boolean chatFollowersOnly;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "stream")
    private List<Like> likes;


    public int getLikeCount() {
        return likes != null ? likes.size() : 0;
    }
}
