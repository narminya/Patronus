package com.demo.patronus.dto.request;

import java.util.UUID;

public record LikeRequest(UUID fromUser, UUID toUser) {
}