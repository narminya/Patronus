package com.demo.patronus.repository;

import com.demo.patronus.models.Block;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BlockRepository extends JpaRepository<Block, UUID> {

    Block findByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);

    Block deleteByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);

    List<Block> findAllByBlockerId(UUID blockerId);

}
