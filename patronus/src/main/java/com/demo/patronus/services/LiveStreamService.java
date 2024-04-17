package com.demo.patronus.services;

import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.models.LiveStream;

import java.util.List;
import java.util.UUID;

public interface LiveStreamService {

    LiveStream save(LiveStream liveStream);
    LiveStream getByUserId(UUID userId);
    LiveStream save(UUID id, StreamPutRequest liveStream);
    void updateStream(String id, boolean status);
    List<LiveStream> getAllFiltered(UUID userId);
    List<LiveStream> getAll();

}
