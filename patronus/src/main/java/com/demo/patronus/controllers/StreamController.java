package com.demo.patronus.controllers;

import com.demo.patronus.dto.request.StreamCreateRequest;
import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.dto.response.LiveStreamResponse;
import com.demo.patronus.dto.response.StreamResponse;
import com.demo.patronus.mapper.StreamMapper;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.User;
import com.demo.patronus.models.redis.StreamHash;
import com.demo.patronus.security.CustomUserDetails;
import com.demo.patronus.services.CacheService;
import com.demo.patronus.services.LiveStreamService;
import com.demo.patronus.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final CacheService cacheService;
    private final UserService userService;

    @Operation(summary = "Get all finished streams")
    @GetMapping("/all/pageable")
    public Page<StreamResponse> getStreams(@ParameterObject Pageable pageable) {
        Page<LiveStream> liveStreamPage = service.listAllStreamsByPage(pageable);
        return liveStreamPage.map(StreamMapper::mapToStreamResponse);
    }

    @Operation(summary = "Get all finished streams of currently authenticated user")
    @GetMapping("/all/user")
    public ResponseEntity<List<StreamResponse>> getAllUsersStreams(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                                   @ParameterObject Pageable pageable) {
        Page<LiveStream> stream = service.getStreams(currentUser.getId(), pageable);
        List<StreamResponse> streamResponse = StreamMapper.mapToStreamResponses(stream.getContent());
        return ResponseEntity.ok(streamResponse);
    }

    @Operation(summary = "Get stream by id")
    @GetMapping("/{streamId}")
    public ResponseEntity<LiveStream> getByStreamId(@PathVariable UUID streamId) {
        LiveStream stream = service.getByStreamId(streamId);
        return ResponseEntity.ok(stream);
    }

    @Operation(summary = "Get live stream of currently authenticated user")
    @GetMapping("/live")
    public ResponseEntity<LiveStreamResponse> getLive(@AuthenticationPrincipal CustomUserDetails currentUser) {
        StreamHash stream = cacheService.getLiveByUserId(currentUser.getId());
        LiveStreamResponse streamResponse = StreamMapper.mapToLiveStreamResponse(stream);
        return ResponseEntity.ok(streamResponse);
    }

    @Operation(summary = "Gets all streams of non blocked by current user")
    @GetMapping("/filter")
    public ResponseEntity<Page<LiveStream>> getAllNonBlocked(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LiveStream> streams = service.getAllFiltered(currentUser.getId(), pageable);
        return ResponseEntity.ok(streams);
    }

    @Operation(summary = "Creates new stream for currently authenticated user")
    @PostMapping
    public ResponseEntity<LiveStream> save(@AuthenticationPrincipal CustomUserDetails currentUser,
                                           @RequestBody StreamCreateRequest request) {
        User user = userService.getUser(currentUser.getUsername());
        LiveStream savedStream = service.save(LiveStream.builder()
                .thumbnailUrl(request.getThumbnailUrl())
                .caption(request.getCaption())
                .live(true)
                .user(user)
                .build());
        cacheService.save(savedStream);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStream);
    }

    @Operation(summary = "Ends livestream for currently authenticated user")
    @PostMapping("/end")
    public ResponseEntity<LiveStream> save(@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.getUser(currentUser.getUsername());
        LiveStream savedStream = service.save(LiveStream.builder()
                .thumbnailUrl(request.getThumbnailUrl())
                .caption(request.getCaption())
                .live(true)
                .user(user)
                .build());
        cacheService.save(savedStream);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStream);
    }

    @Operation(summary = "Updates streams ingress info")
    @PutMapping("/{streamId}/ingress")
    public ResponseEntity<LiveStreamResponse> updateIngress(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                   @PathVariable UUID streamId,
                                                   @RequestBody StreamPutRequest request) {

        StreamHash stream = cacheService.updateIngressInfo(streamId, request);
        LiveStreamResponse streamResponse = StreamMapper.mapToLiveStreamResponse(stream);
        return ResponseEntity.ok(streamResponse);
    }



    @Operation(summary = "Updates chat details and thumbnail of stream")
    @PutMapping("/{streamId}/details")
    public ResponseEntity<Void> updateStream(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @PathVariable UUID streamId,
                                             @RequestParam StreamPatchRequest request) {
        cacheService.updateStreamInfo(streamId, request);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "User archives stream")
    @PostMapping("/{streamId}/archive")
    public ResponseEntity<Void> archive(@AuthenticationPrincipal CustomUserDetails currentUser,
                                        @PathVariable UUID streamId) {
        service.archiveStream(streamId);
        return ResponseEntity.noContent().build();
    }
}
