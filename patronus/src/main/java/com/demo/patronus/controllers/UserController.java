package com.demo.patronus.controllers;


import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
private final UserService userService;
    @GetMapping("/{userId}/blockedBy/{blockedId}")
    public ResponseEntity<Boolean> isBlockedByUser(@PathVariable UUID userId, @PathVariable UUID blockedId) {
        boolean isBlocked = userService.isBlockedByUser(userId, blockedId);
        return new ResponseEntity<>(isBlocked, HttpStatus.OK);
    }
}
