package com.demo.patronus.controllers;

import com.demo.patronus.dto.request.StreamCreateRequest;
import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.dto.request.StreamUpdateRequest;
import com.demo.patronus.dto.response.StreamResponse;
import com.demo.patronus.mapper.StreamMapper;
import com.demo.patronus.models.Follow;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.User;
import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.LiveStreamService;
import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/streams")
@RequiredArgsConstructor
public class StreamController {

    private final LiveStreamService service;
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<StreamResponse>> getAllUsersStreams(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<LiveStream> stream = service.getStreams(currentUser.getId());
        List<StreamResponse> streamResponse = StreamMapper.mapToStreamResponses(stream);
        return ResponseEntity.ok(streamResponse);
    }


    @GetMapping("/{streamId}")
    public ResponseEntity<LiveStream> getByStreamId(UUID streamId) {
        LiveStream stream = service.getByStreamId(streamId);
        return ResponseEntity.ok(stream);
    }

    @GetMapping("/live")
    public ResponseEntity<StreamResponse> getLiveById(@AuthenticationPrincipal CustomUserDetails currentUser) {
        LiveStream stream = service.getLiveByUserId(currentUser.getId());
        StreamResponse streamResponse = StreamMapper.mapToStreamResponse(stream);
        return ResponseEntity.ok(streamResponse);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LiveStream>> getAllNonBlocked(@AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        List<LiveStream> streams = service.getAllFiltered(currentUser.getId());
        return ResponseEntity.ok(streams);
    }

    @GetMapping
    public ResponseEntity<List<StreamResponse>> getAll() {
        List<LiveStream> streams = service.getAll();
        List<StreamResponse> streamResponses = StreamMapper.mapToStreamResponses(streams);
        return ResponseEntity.ok(streamResponses);
    }

    @PostMapping
    public ResponseEntity<LiveStream> save(@AuthenticationPrincipal CustomUserDetails currentUser,
                                           @RequestBody StreamCreateRequest request) {
        User user = userService.getUser(currentUser.getUsername());
        LiveStream savedStream = service.save(LiveStream.builder()
                .thumbnailUrl(request.getThumbnailUrl())
                .caption(request.getCaption())
                .user(user)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStream);
    }

    @PutMapping("/{streamId}")
    public ResponseEntity<LiveStream> updateStream(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                   @PathVariable UUID streamId,
                                                   @RequestBody StreamPutRequest request) {
        LiveStream updatedStream = service.save(streamId, request);
        return ResponseEntity.ok(updatedStream);
    }

    @PatchMapping("/{ingressId}/ingress")
    public ResponseEntity<Void> updateIngress(@PathVariable String ingressId,
                                              @RequestParam Boolean status) {
        service.updateStreamStatus(ingressId, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{streamId}/key")
    public ResponseEntity<Void> updateStreamKey(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @PathVariable UUID streamId,
                                             @RequestParam StreamUpdateRequest request) {
        service.updateStreamKey(streamId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{streamId}/details")
    public ResponseEntity<Void> updateStream(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @PathVariable UUID streamId,
                                             @RequestParam StreamPatchRequest request) {
        service.updateStreamInfo(streamId, request);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{streamId}/archive")
    public ResponseEntity<Void> archive(@AuthenticationPrincipal CustomUserDetails currentUser,
                                        @PathVariable UUID streamId) {
        service.archiveStream(streamId);
        return ResponseEntity.noContent().build();
    }
}
