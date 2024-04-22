package com.demo.patronus.services.impl;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.dto.request.StreamUpdateRequest;
import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.repository.LiveStreamRepository;
import com.demo.patronus.services.LiveStreamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public LiveStream save(UUID id, StreamPutRequest liveStream) {
        LiveStream stream = repository.findById(id).orElseThrow();
        stream.setStreamKey(liveStream.getStreamKey());
        stream.setServerUrl(liveStream.getUrl());
        stream.setIngressId(liveStream.getIngressId());
        return repository.save(stream);
    }

    @Override
    @Transactional
    public void updateStreamStatus(String id, boolean status) {
        LiveStream stream = repository.findByIngressId(id).orElseThrow();
        stream.setLive(status);
        repository.save(stream);
    }

    @Override
    public void updateStreamInfo(UUID id, StreamPatchRequest status) {
        LiveStream stream = repository.findById(id).orElseThrow();

        stream.setUpdatedAt(LocalDateTime.now());
        stream.setThumbnailUrl(status.getThumbnailUrl());
        stream.setChatDelayed(status.getChatDelayed());
        stream.setChatEnabled(status.getChatEnabled());
        stream.setChatFollowersOnly(status.getChatFollowersOnly());

        repository.save(stream);
    }

    @Override
    public void updateStreamKey(UUID id, StreamUpdateRequest status) {
        LiveStream stream = repository.findById(id).orElseThrow();
        stream.setStreamKey(status.getStreamKey());
        stream.setIngressId(status.getIngressId());
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
    public void archiveStream(UUID streamId) {
        LiveStream stream = repository.findById(streamId).orElseThrow();
        stream.setArchived(true);
        repository.save(stream);
    }

    @Override
    public LiveStream getLiveByUserId(UUID userId) {
        return repository.findLiveByUserId(userId)
                .orElseThrow(() -> new StreamNotFoundException("Stream not found for user with ID: " + userId));
    }

    @Override
    public List<LiveStream> getStreams(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public LiveStream getByUserId(UUID userId, UUID streamId) {
        return null;
    }

    @Override
    public LiveStream getByStreamId(UUID streamId) {
        return repository.findById(streamId)
                .orElseThrow(() -> new StreamNotFoundException("Stream not found for user with ID: " + streamId));
    }


}
