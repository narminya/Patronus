package com.demo.patronus.services;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.redis.StreamHash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface LiveStreamService {

    StreamHash save(LiveStream liveStream);
    StreamHash save(UUID id, StreamPutRequest liveStream);
    List<StreamHash> getAllLiveStreams();
    StreamHash updateStreamInfo(UUID id, StreamPatchRequest status);
    StreamHash updateIngressInfo(UUID id, StreamPutRequest status);
    StreamHash getLiveByUserId(UUID userId);
    void removeStream(UUID userId);
    StreamHash getById(UUID streamId);
}
