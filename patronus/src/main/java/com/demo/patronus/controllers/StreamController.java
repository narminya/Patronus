package com.demo.patronus.controllers;

import com.demo.patronus.dto.request.StreamCreateRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.services.LiveStreamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/streams")
public class StreamController {

    private final LiveStreamService service;

    public StreamController(LiveStreamService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<LiveStream> getById(@PathVariable UUID userId) {
        LiveStream stream = service.getByUserId(userId);
        return ResponseEntity.ok(stream);
    }

    @GetMapping("/{userId}/filter")
    public ResponseEntity<List<LiveStream>> getAllNonBlocked(@PathVariable UUID userId) {
        List<LiveStream> streams = service.getAllFiltered(userId);
        return ResponseEntity.ok(streams);
    }
    @GetMapping
    public ResponseEntity<List<LiveStream>> getAll() {
        List<LiveStream> streams = service.getAll();
        return ResponseEntity.ok(streams);
    }


    @PostMapping
    public ResponseEntity<LiveStream> save(@RequestBody StreamCreateRequest request) {
        LiveStream savedStream = service.save(LiveStream.builder()
                .thumbnailUrl(request.getThumbnailUrl())
                .caption(request.getCaption())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStream);
    }

    @PutMapping("/{streamId}")
    public ResponseEntity<LiveStream> updateStream(@PathVariable UUID streamId,
                                                   @RequestBody StreamPutRequest request) {
        LiveStream updatedStream = service.save(streamId, request);
        return ResponseEntity.ok(updatedStream);
    }

    @PatchMapping("/{ingressId}")
    public ResponseEntity<Void> updateIngress(@PathVariable String ingressId,
                                              @RequestParam Boolean status) {
        service.updateStream(ingressId, status);
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{streamId}")
//    public ResponseEntity<Void> update(@PathVariable UUID streamId,
//                                              @RequestBody Boolean status) {
//        service.updateStream(streamId, status);
//        return ResponseEntity.noContent().build();
//    }
}
