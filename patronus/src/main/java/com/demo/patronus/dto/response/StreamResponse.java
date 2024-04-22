package com.demo.patronus.dto.response;

import java.time.LocalDateTime;

public record StreamResponse(String id, String caption, String thumbnailUrl, StreamResponse.UserResponse user,
                             Boolean live,
                             LocalDateTime created, Integer like,
                             String ingressId,
                             String serverUrl, String streamKey) {
    public record UserResponse(String username, String imageUrl) {

    }

}


