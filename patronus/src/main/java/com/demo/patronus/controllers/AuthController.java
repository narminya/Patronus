package com.demo.patronus.controllers;

import com.demo.patronus.dto.request.AuthRequest;
import com.demo.patronus.dto.request.RegisterRequest;
import com.demo.patronus.dto.response.AuthResponse;
import com.demo.patronus.exception.DuplicatedUserInfoException;
import com.demo.patronus.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        if (service.hasUserWithUsername(request.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", request.getUsername()));
        }
        if (service.hasUserWithEmail(request.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", request.getEmail()));
        }

        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid   @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
