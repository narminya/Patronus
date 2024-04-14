package com.demo.patronus.services;

import com.demo.patronus.dto.request.StreamUpdateRequest;
import com.demo.patronus.models.LiveStream;

import java.util.List;
import java.util.UUID;

public interface LiveStreamService {

    LiveStream save(LiveStream liveStream);
    LiveStream getByUserId(UUID userId);
    LiveStream save(UUID id, StreamUpdateRequest liveStream);
    void updateStream(UUID id, boolean status);
    List<LiveStream> getAllFiltered(UUID userId);
    List<LiveStream> getAll();

}
