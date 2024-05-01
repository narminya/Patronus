package com.demo.patronus.repository;

import com.demo.patronus.models.jpa.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository  extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);
    @Query(value = "SELECT s.* FROM refreshTokens s inner join JOIN user u ON u.id = s.user_id " +
            "WHERE u.id != :id and (t.expired=false or t.revoked=false)", nativeQuery = true)

    List<RefreshToken> findAllValidTokenByUser(UUID id);

}
