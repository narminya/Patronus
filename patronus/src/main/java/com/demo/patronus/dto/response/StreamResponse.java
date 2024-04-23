package com.demo.patronus.dto.response;

import java.time.LocalDateTime;

public record StreamResponse(String id, String caption, String thumbnailUrl,
                             StreamResponse.UserResponse user,
                             LocalDateTime created, Integer like
                           ) {
    public record UserResponse(String username, String imageUrl) {

    }

}



