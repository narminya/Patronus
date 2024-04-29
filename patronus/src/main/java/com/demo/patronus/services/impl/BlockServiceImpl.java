package com.demo.patronus.services.impl;

import com.demo.patronus.exception.UserNotFoundException;
import com.demo.patronus.models.Block;
import com.demo.patronus.models.User;
import com.demo.patronus.repository.BlockRepository;
import com.demo.patronus.repository.UserRepository;
import com.demo.patronus.services.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
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
}
