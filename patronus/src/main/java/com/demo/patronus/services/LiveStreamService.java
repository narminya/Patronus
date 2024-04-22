package com.demo.patronus.services;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.dto.request.StreamUpdateRequest;
import com.demo.patronus.models.LiveStream;

import java.util.List;
import java.util.UUID;

public interface LiveStreamService {

    LiveStream save(LiveStream liveStream);
    LiveStream getLiveByUserId(UUID userId);
    List<LiveStream> getStreams(UUID userId);
    LiveStream getByUserId(UUID userId, UUID streamId);
    LiveStream getByStreamId( UUID streamId);
    LiveStream save(UUID id, StreamPutRequest liveStream);
    void updateStreamStatus(String id, boolean status);
    void updateStreamInfo(UUID id, StreamPatchRequest status);
    void updateStreamKey(UUID id, StreamUpdateRequest status);
    List<LiveStream> getAllFiltered(UUID userId);
    List<LiveStream> getAll();
    void archiveStream(UUID streamId);
}
