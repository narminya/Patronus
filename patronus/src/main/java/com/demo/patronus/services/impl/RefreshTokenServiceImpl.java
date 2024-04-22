package com.demo.patronus.services.impl;

import com.demo.patronus.exception.TokenNotFoundException;
import com.demo.patronus.exception.TokenRefreshException;
import com.demo.patronus.models.RefreshToken;
import com.demo.patronus.repository.TokenRepository;
import com.demo.patronus.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final TokenRepository tokenRepository;
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        RefreshToken token = tokenRepository.findByToken(refreshToken.getToken()).orElseThrow(() -> new TokenNotFoundException(""));
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.save(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
