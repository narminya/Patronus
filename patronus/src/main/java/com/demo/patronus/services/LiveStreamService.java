package com.demo.patronus.services;

import com.demo.patronus.models.LiveStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LiveStreamService {

    LiveStream save(LiveStream liveStream);
    Page<LiveStream> getStreams(UUID userId, Pageable pageable);
    LiveStream getByStreamId( UUID streamId);
    Page<LiveStream> getAllFiltered(UUID userId, Pageable pageable);
    Page<LiveStream> listAllStreamsByPage(Pageable pageable);
    void archiveStream(UUID streamId, UUID userId);
    LiveStream endStream(UUID streamId);
}
