package com.demo.patronus.repository;

import com.demo.patronus.models.redis.StreamHash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface RedisRepository extends CrudRepository<StreamHash, UUID> {
    Optional<StreamHash> findByUserId(UUID userId);
    List<StreamHash> findAll(Pageable pageable);

}