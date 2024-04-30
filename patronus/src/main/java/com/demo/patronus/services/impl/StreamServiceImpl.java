package com.demo.patronus.services.impl;

import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.repository.StreamRepository;
import com.demo.patronus.services.LiveStreamService;
import com.demo.patronus.services.StorageService;
import com.demo.patronus.services.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements StreamService {
    private final StreamRepository repository;
//    private final LiveStreamService cacheService;
    private final StorageService storageService;

    @Override
    public LiveStream save(LiveStream liveStream) {
        return repository.save(liveStream);
    }

    public Page<LiveStream> getAllFiltered(UUID userId, Pageable pageable) {
        return repository.findAll(userId, pageable);
    }

    @Override
    public void archiveStream(UUID streamId, UUID userId) {
        repository.updateByStreamIdAndUserId(streamId, userId);
    }

    @Override
    public Page<LiveStream> getStreams(UUID userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
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
    public LiveStream endStream(UUID userId) {
        var live = repository.findLiveByUserId(userId)
                .orElseThrow(() -> new StreamNotFoundException(userId));
        live.setLive(false);
//        cacheService.removeStream(streamId);
        return repository.save(live);
    }

    @Override
    public void uploadThumbnail(UUID streamId, MultipartFile poster) {
        LiveStream stream = repository.findById(streamId).orElseThrow();
        String uploadedFile = storageService.uploadFile(poster);
        stream.setThumbnailUrl(uploadedFile);
        repository.save(stream);
    }


}
