package com.demo.patronus.controllers;

import com.demo.patronus.models.Block;
import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/block")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @GetMapping
    public ResponseEntity<List<Block>> getBlockedUsers(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Block> blockedUsers = blockService.getBlockedUsers(currentUser.getId());
        return ResponseEntity.ok(blockedUsers);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Block> blockUser(@AuthenticationPrincipal CustomUserDetails currentUser,
                                           @PathVariable UUID userId) {
        Block blocked = blockService.blockUser(currentUser.getId(), userId);
        return ResponseEntity.ok(blocked);
    }

    @PostMapping("/unblock/{blockId}")
    public ResponseEntity<Block> unblockUser(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @PathVariable UUID blockId) {
        Block unblocked = blockService.unBlockUser(currentUser.getId(), blockId);
        return ResponseEntity.ok(unblocked);
    }

    @GetMapping("/blockedBy/{blockedId}")
    public ResponseEntity<Boolean> isBlockedByUser(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                   @PathVariable UUID blockedId) {
        boolean isBlocked = blockService.blockedByUser(currentUser.getId(), blockedId);
        return ResponseEntity.ok(isBlocked);
    }
}
