package com.demo.patronus.controllers;

import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/like")
public class LikeController {

    private final LikeService service;
    @Operation(summary = "User likes streams")
    @PostMapping("/{streamId}")
    public ResponseEntity<Void> likeStream (@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID streamId) {
        service.likeStream(currentUser.getId(), streamId);
        return ResponseEntity.noContent().build();
    }
}
