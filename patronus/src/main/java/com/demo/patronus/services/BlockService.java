package com.demo.patronus.services;

import com.demo.patronus.models.Block;

import java.util.List;
import java.util.UUID;

public interface BlockService {
    Block blockUser(UUID userId, UUID blockedId);
    Block unBlockUser(UUID  userId,UUID blockedId);
    boolean blockedByUser(UUID userId, UUID blockedId);
    List<Block> getBlockedUsers(UUID  userId);
}
