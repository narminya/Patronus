package com.demo.patronus.controllers;


import com.demo.patronus.dto.response.FollowResponse;
import com.demo.patronus.mapper.FollowMapper;
import com.demo.patronus.models.Follow;
import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    @GetMapping("/{followedId}/followedBy")
    public ResponseEntity<Boolean> isFollowedByUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID followedId) {
        boolean isBlocked = followService.followedByUser(currentUser.getId(), followedId);
        return new ResponseEntity<>(isBlocked, HttpStatus.OK);
    }

    @PostMapping("/{followId}")
    public ResponseEntity<Follow> followUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID followId) {
        Follow followed = followService.followUser(currentUser.getId(), followId);
        return new ResponseEntity<>(followed, HttpStatus.OK);
    }

    @PostMapping("/{followId}/unfollow")
    public ResponseEntity<Follow> unfollowUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable UUID followId) {
        Follow followed = followService.unfollowUser(currentUser.getId(), followId);
        return new ResponseEntity<>(followed, HttpStatus.OK);
    }
    @GetMapping("/followers")
    public ResponseEntity<List<FollowResponse>> followedBy(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Follow> followed = followService.getFollowers(currentUser.getId());
        List<FollowResponse> followResponses = FollowMapper.mapToFollowsResponse(followed);
        return new ResponseEntity<>(followResponses, HttpStatus.OK);
    }
    @GetMapping("/followed")
    public ResponseEntity<List<FollowResponse>> followed(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Follow> followed = followService.getFollowedUsers(currentUser.getId());
        List<FollowResponse> followResponses = FollowMapper.mapToFollowsResponse(followed);
        return new ResponseEntity<>(followResponses, HttpStatus.OK);
    }
}
