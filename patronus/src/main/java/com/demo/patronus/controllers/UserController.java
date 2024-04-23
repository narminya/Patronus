package com.demo.patronus.controllers;


import com.demo.patronus.dto.response.FollowResponse;
import com.demo.patronus.dto.response.UserResponse;
import com.demo.patronus.mapper.FollowMapper;
import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import com.demo.patronus.models.User;
import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        User user = userService.getUserByUsername(username);

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


    @PostMapping("/blocked")
    public ResponseEntity<List<Block>> blockedUsers(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Block> blocked = userService.getBlockedUsers(currentUser.getId());
        return new ResponseEntity<>(blocked, HttpStatus.OK);
    }
    @PostMapping("/block/{blockId}")
    public ResponseEntity<Block> blockUser(@AuthenticationPrincipal CustomUserDetails currentUser,  @PathVariable UUID blockId) {
        Block blocked = userService.blockUser(currentUser.getId(), blockId);
        return new ResponseEntity<>(blocked, HttpStatus.OK);
    }
    @PostMapping("/unblock/{blockId}")
    public ResponseEntity<Block> unblockUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID blockId) {
        Block blocked = userService.unBlockUser(currentUser.getId(), blockId);
        return new ResponseEntity<>(blocked, HttpStatus.OK);
    }
    @GetMapping("/blockedBy/{blockedId}")
    public ResponseEntity<Boolean> blockedByUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID blockedId) {
        boolean isBlocked = userService.blockedByUser(currentUser.getId(), blockedId);
        return new ResponseEntity<>(isBlocked, HttpStatus.OK);
    }
    @GetMapping("/followedBy/{followedId}")
    public ResponseEntity<Boolean> isFollowedByUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID followedId) {
        boolean isBlocked = userService.blockedByUser(currentUser.getId(), followedId);
        return new ResponseEntity<>(isBlocked, HttpStatus.OK);
    }

    @PostMapping("/follow/{followId}")
    public ResponseEntity<Follow> followUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID followId) {
        Follow followed = userService.followUser(currentUser.getId(), followId);
        return new ResponseEntity<>(followed, HttpStatus.OK);
    }

    @PostMapping("unfollow/{followId}")
    public ResponseEntity<Follow> unfollowUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID followId) {
        Follow followed = userService.unfollowUser(currentUser.getId(), followId);
        return new ResponseEntity<>(followed, HttpStatus.OK);
    }
    @GetMapping("/followedBy")
    public ResponseEntity<List<FollowResponse>> followedBy(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Follow> followed = userService.getFollowers(currentUser.getId());
        List<FollowResponse> followResponses = FollowMapper.mapToFollowsResponse(followed);
        return new ResponseEntity<>(followResponses, HttpStatus.OK);
    }
    @GetMapping("/followed")
    public ResponseEntity<List<FollowResponse>> followed(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Follow> followed = userService.getFollowedUsers(currentUser.getId());
        List<FollowResponse> followResponses = FollowMapper.mapToFollowsResponse(followed);
        return new ResponseEntity<>(followResponses, HttpStatus.OK);
    }

}
