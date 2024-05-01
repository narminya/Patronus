package com.demo.patronus.services.impl;

import com.demo.patronus.dto.request.AuthRequest;
import com.demo.patronus.dto.request.RegisterRequest;
import com.demo.patronus.dto.response.AuthResponse;
import com.demo.patronus.enums.ERole;
import com.demo.patronus.models.jpa.RefreshToken;
import com.demo.patronus.models.jpa.Role;
import com.demo.patronus.models.jpa.User;
import com.demo.patronus.repository.RoleRepository;
import com.demo.patronus.repository.TokenRepository;
import com.demo.patronus.repository.UserRepository;
import com.demo.patronus.security.TokenProvider;
import com.demo.patronus.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${app.jwtExpirationMs}")
    private Long jwtExpirationMs;
    @Value("${app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        var savedUser = userRepository.save(mapSignUpRequestToUser(request));
        var jwtToken = generateToken(request.getUsername(), request.getPassword());
        var refreshToken = generateRefreshToken(request.getUsername(), request.getPassword());
        saveUserToken(savedUser, jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {

        var jwtToken = generateToken(request.getUsername(), request.getPassword());
        var refreshToken = generateRefreshToken(request.getUsername(), request.getPassword());

        AuthResponse response = new AuthResponse();
        response.setAccessToken(jwtToken);
        response.setRefreshToken(refreshToken);

        return response;
    }
    private User mapSignUpRequestToUser(RegisterRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());

        Optional<Role> optionalRole = roleRepository.findByName(ERole.valueOf("ADMIN"));

        if (optionalRole.isPresent()) {
            user.getRoles().add(optionalRole.get());
        } else {
            throw new IllegalArgumentException("Role not found");
        }
        return user;
    }
    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userUsername;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userUsername = getUsernameFromToken(request).orElseThrow();

            var user = userRepository.findByUsername(userUsername)
                    .orElseThrow();

        String encodedPassword = passwordEncoder.encode(request.getParameter("password"));

        if (!passwordEncoder.matches(encodedPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
                var accessToken = generateToken(userUsername, user.getPassword());
                revokeUsersTokens(user);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

    }

    private Optional<String> getUsernameFromToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith("Bearer")) {
            return Optional.of(tokenHeader.replace("Bearer ", ""))
                    .flatMap(tokenProvider::validateTokenAndGetJws)
                    .map(jws -> jws.getPayload().getSubject());
        }
        return Optional.empty();
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = RefreshToken.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .expiryDate(Instant.now().plusSeconds(jwtRefreshExpirationMs))
                .build();
        tokenRepository.save(token);
    }


    public void revokeUsersTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public String generateToken(
            String username, String password
    ) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }


    public String generateRefreshToken(
            String username, String password
    ) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }
}
