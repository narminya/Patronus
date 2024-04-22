package com.demo.patronus.services;

import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import com.demo.patronus.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUser(String username);
    User getUserById(UUID  userId);
    User getUserByUsername(String username);
    Block blockUser(UUID  userId, UUID blockedId);
    Block unBlockUser(UUID  userId,UUID blockedId);
    boolean blockedByUser(UUID userId, UUID blockedId);
    List<Block> getBlockedUsers(UUID  userId);
    List<Follow> getFollowedUsers(UUID userId);
    List<Follow> getFollowedByUsers(UUID userId);
    Follow followUser(UUID userId, UUID followedId);
    Follow unfollowUser(UUID  userId, UUID followedId);
    boolean followedByUser(UUID userId, UUID followedId);


}
