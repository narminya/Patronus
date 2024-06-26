package com.demo.patronus.controllers;


import com.demo.patronus.dto.response.UserResponse;
import com.demo.patronus.models.jpa.User;
import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.getUserById(currentUser.getId());

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .bio(user.getBio())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .name(user.getName()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        User user = userService.getUser(username);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .name(user.getName()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{userId}/id")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .name(user.getName()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
