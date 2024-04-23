package com.demo.patronus.services.impl;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.dto.request.StreamUpdateRequest;
import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.redis.StreamHash;
import com.demo.patronus.repository.LiveStreamRepository;
import com.demo.patronus.repository.RedisRepository;
import com.demo.patronus.services.LiveStreamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return repository.save(liveStream);
    }

    public Page<LiveStream> getAllFiltered(UUID userId, Pageable pageable) {
        return repository.findAll(userId, pageable);
    }

    @Override
    public void archiveStream(UUID streamId) {
        LiveStream stream = repository.findById(streamId).orElseThrow();
        stream.setArchived(true);
        repository.save(stream);
    }

    @Override
    public Page<LiveStream> getStreams(UUID userId,Pageable pageable) {
        return repository.findAllByUserId(userId,pageable);
    }

    @Override
    public Page<LiveStream> listAllStreamsByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public LiveStream getByStreamId(UUID streamId) {
        return repository.findById(streamId)
                .orElseThrow(() -> new StreamNotFoundException(streamId));
    }


}
