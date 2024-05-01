package com.demo.patronus.repository;

import com.demo.patronus.models.jpa.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BlockRepository extends JpaRepository<Block, UUID> {
    Block findByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);
    Block deleteByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);
    List<Block> findAllByBlockerId(UUID blockerId);

}
