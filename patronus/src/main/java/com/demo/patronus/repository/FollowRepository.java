package com.demo.patronus.repository;

import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {

    List<Follow> findFollowByFollower_Id(UUID followerId);
    Follow findByFollowerIdAndFollowingId(UUID follower, UUID following);
}
