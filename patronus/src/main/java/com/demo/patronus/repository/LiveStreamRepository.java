package com.demo.patronus.repository;

import com.demo.patronus.models.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LiveStreamRepository extends JpaRepository<LiveStream, UUID> {

    @Query(value = "SELECT * FROM streams WHERE user_id = :userId", nativeQuery = true)
    Optional<LiveStream> findByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT s.* FROM streams s LEFT JOIN block b ON s.user_id = b.blocker_user_id " +
            "WHERE b.blocked_user_id != :userId OR b.blocked_user_id IS NULL", nativeQuery = true)
    List<LiveStream> findAll(@Param("userId") UUID userId);

}
