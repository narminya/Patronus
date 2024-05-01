package com.demo.patronus.services.impl;

import com.demo.patronus.exception.UserNotFoundException;
import com.demo.patronus.models.jpa.Follow;
import com.demo.patronus.models.jpa.User;
import com.demo.patronus.repository.FollowRepository;
import com.demo.patronus.repository.UserRepository;
import com.demo.patronus.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    @Override
    public List<Follow> getFollowedUsers(UUID userId) {
        return followRepository.findFollowByFollower_Id(userId);
    }

    @Override
    public List<Follow> getFollowers(UUID userId) {
        return followRepository.findFollowByFollowingId(userId);
    }

    @Override
    public Follow followUser(UUID userId, UUID followedId) {
        User self = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userId.equals(followedId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        User following = userRepository.findById(followedId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (followRepository.findByFollowerIdAndFollowingId(userId, followedId)!=null) {
            throw new IllegalStateException("Already following this user");
        }

        Follow follow = Follow.builder()
                .follower(self)
                .following(following)
                .build();
        return followRepository.save(follow);
    }


    @Override
    public Follow unfollowUser(UUID userId, UUID followedId) {
        return null;
    }

    @Override
    public boolean followedByUser(UUID userId, UUID followedId) {
        try {
            if (userId.equals(followedId)) {
                return false;
            }
            return followRepository.findByFollowerIdAndFollowingId(userId, followedId)!=null;
        } catch (Exception e) {
            return false;
        }
    }
}
