package com.demo.patronus.services.impl;

import com.demo.patronus.exception.UserNotFoundException;
import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import com.demo.patronus.models.User;
import com.demo.patronus.repository.BlockRepository;
import com.demo.patronus.repository.FollowRepository;
import com.demo.patronus.repository.UserRepository;
import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final FollowRepository followRepository;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public Block blockUser(UUID userId, UUID blockedId) {
        try {
            User self = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

            if (userId.equals(blockedId)) {
                throw new IllegalArgumentException("Cannot block yourself");
            }
            User blockedUser = userRepository.findById(blockedId).orElseThrow(() -> new UserNotFoundException("User not found"));

            if (blockRepository.findByBlockerIdAndBlockedId(userId, blockedId) != null) {
                throw new IllegalStateException("Already blocked");
            }

            Block block = new Block();
            block.setBlocker(self);
            block.setBlocked(blockedUser);
            return blockRepository.save(block);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Block unBlockUser(UUID userId, UUID blockedId) {
        try {
            if (userId.equals(blockedId)) {
                throw new IllegalArgumentException("Cannot unblock yourself");
            }
            if (blockRepository.findByBlockerIdAndBlockedId(userId, blockedId) == null) {
                throw new IllegalStateException("Not blocked");
            }

            return blockRepository.deleteByBlockerIdAndBlockedId(userId, blockedId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean blockedByUser(UUID userId, UUID blockedId) {
        try {
            if (userId.equals(blockedId)) {
                return false;
            }
            Block existingBlock = blockRepository.findByBlockerIdAndBlockedId(userId, blockedId);
            return existingBlock != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Block> getBlockedUsers(UUID userId) {
        return blockRepository.findAllByBlockerId(userId);
    }

    @Override
    public List<Follow> getFollowedUsers(UUID userId) {
       return followRepository.findFollowByFollower_Id(userId);
    }

    @Override
    public List<Follow> getFollowedByUsers(UUID userId) {
        return null;
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
