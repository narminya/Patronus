package com.demo.patronus.controllers;


import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PostMapping("/{userId}/blocked")
    public ResponseEntity<List<Block>> blockedUsers(@PathVariable UUID userId) {
        List<Block> blocked = userService.getBlockedUsers(userId);
        return new ResponseEntity<>(blocked, HttpStatus.OK);
    }
    @PostMapping("/{userId}/block/{blockId}")
    public ResponseEntity<Block> blockUser(@PathVariable UUID userId, @PathVariable UUID blockId) {
        Block blocked = userService.blockUser(userId, blockId);
        return new ResponseEntity<>(blocked, HttpStatus.OK);
    }
    @PostMapping("/{userId}/unblock/{blockId}")
    public ResponseEntity<Block> unblockUser(@PathVariable UUID userId, @PathVariable UUID blockId) {
        Block blocked = userService.unBlockUser(userId, blockId);
        return new ResponseEntity<>(blocked, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followedBy/{blockedId}")
    public ResponseEntity<Boolean> isFollowedByUser(@PathVariable UUID userId, @PathVariable UUID blockedId) {
        boolean isBlocked = userService.isBlockedByUser(userId, blockedId);
        return new ResponseEntity<>(isBlocked, HttpStatus.OK);
    }
//    @GetMapping("/{userId}/followedBy")
//    public ResponseEntity<Boolean> followedBy(@PathVariable UUID userId) {
//        boolean isBlocked = userService.getFollowedUsers(userId, blockedId);
//        return new ResponseEntity<>(isBlocked, HttpStatus.OK);
//    }
    @PostMapping("/{userId}/follow/{followId}")
    public ResponseEntity<Follow> followUser(@PathVariable UUID userId, @PathVariable UUID followId) {
        Follow followed = userService.followUser(userId, followId);
        return new ResponseEntity<>(followed, HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow/{followId}")
    public ResponseEntity<Follow> unfollowUser(@PathVariable UUID userId, @PathVariable UUID followId) {
        Follow followed = userService.unfollowUser(userId, followId);
        return new ResponseEntity<>(followed, HttpStatus.OK);
    }
}
