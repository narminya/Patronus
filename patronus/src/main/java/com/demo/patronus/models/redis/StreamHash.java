package com.demo.patronus.models.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash
public class StreamHash {
    @Id
    private UUID id;
    @Indexed
    private UUID userId;
    private String caption;
    private String description;
    private String thumbnailUrl;
    private String ingressId;
    private String serverUrl;
    private String streamKey;
    private boolean chatEnabled;
    private boolean chatDelayed;
    private boolean chatFollowersOnly;
}