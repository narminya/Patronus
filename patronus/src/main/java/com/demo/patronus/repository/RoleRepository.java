package com.demo.patronus.repository;

import com.demo.patronus.enums.ERole;
import com.demo.patronus.models.Block;
import com.demo.patronus.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
