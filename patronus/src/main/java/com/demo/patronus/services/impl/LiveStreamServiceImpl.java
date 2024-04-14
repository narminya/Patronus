package com.demo.patronus.services.impl;

import com.demo.patronus.dto.request.StreamUpdateRequest;
import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.repository.LiveStreamRepository;
import com.demo.patronus.services.LiveStreamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LiveStreamServiceImpl implements LiveStreamService {
    private final LiveStreamRepository repository;
    @Override
    public LiveStream save(LiveStream liveStream) {
        return  repository.save(liveStream);
    }

    @Override
    @Transactional
    public LiveStream save(UUID id, StreamUpdateRequest liveStream) {
        LiveStream stream = repository.findById(id).orElseThrow();
        stream.setStreamKey(liveStream.getStreamKey());
        stream.setServerUrl(liveStream.getUrl());
        stream.setIngressId(liveStream.getIngressId());
        return repository.save(stream);
    }

    @Override
    @Transactional
    public void updateStream(UUID id, boolean status) {
        LiveStream stream = repository.findById(id).orElseThrow();
        stream.setLive(status);
        repository.save(stream);
    }

    @Override
    public List<LiveStream> getAllFiltered(UUID userId) {
      return repository.findAll(userId);
    }

    @Override
    public List<LiveStream> getAll() {
        return repository.findAll();
    }

    @Override
    public LiveStream getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new StreamNotFoundException("Stream not found for user with ID: " + userId));
    }



}
