package com.demo.patronus.services.impl;

import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.repository.StreamRepository;
import com.demo.patronus.services.LiveStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LiveStreamServiceImpl implements LiveStreamService {
    private final StreamRepository repository;
    private final CacheServiceImpl cacheService;
    @Override
    public LiveStream save(LiveStream liveStream) {
        return repository.save(liveStream);
    }

    public Page<LiveStream> getAllFiltered(UUID userId, Pageable pageable) {
        return repository.findAll(userId, pageable);
    }

    @Override
    public void archiveStream(UUID streamId) {
        repository.updateByStreamId(streamId);
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

    @Override
    public LiveStream endStream(UUID streamId) {
        var live = repository.findById(streamId)
                .orElseThrow(() -> new StreamNotFoundException(streamId));
        live.setLive(false);
        cacheService.removeStream(streamId);
       return repository.save(live);
    }



}
