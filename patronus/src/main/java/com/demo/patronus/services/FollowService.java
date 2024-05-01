package com.demo.patronus.services;

import com.demo.patronus.models.jpa.Follow;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    List<Follow> getFollowedUsers(UUID userId);
    List<Follow> getFollowers(UUID userId);
    Follow followUser(UUID userId, UUID followedId);
    Follow unfollowUser(UUID  userId, UUID followedId);
    boolean followedByUser(UUID userId, UUID followedId);
}
