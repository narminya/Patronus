package com.demo.patronus.repository;

import com.demo.patronus.models.jpa.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {

}
