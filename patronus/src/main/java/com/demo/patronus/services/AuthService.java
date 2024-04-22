package com.demo.patronus.services;

import com.demo.patronus.dto.request.AuthRequest;
import com.demo.patronus.dto.request.RegisterRequest;
import com.demo.patronus.dto.response.AuthResponse;
import com.demo.patronus.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void revokeUsersTokens(User user);


    boolean hasUserWithUsername(String username);


    boolean hasUserWithEmail(String email);
}