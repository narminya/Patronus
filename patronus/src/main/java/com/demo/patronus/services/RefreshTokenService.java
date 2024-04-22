package com.demo.patronus.services;

import com.demo.patronus.models.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);


}
