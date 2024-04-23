package com.demo.patronus.repository;

import com.demo.patronus.models.redis.StreamHash;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface RedisRepository extends CrudRepository<StreamHash, UUID> {
    Optional<StreamHash> findByUserId(UUID userId);
}