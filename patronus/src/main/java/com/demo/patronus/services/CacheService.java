package com.demo.patronus.services;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.redis.StreamHash;

import java.util.UUID;

public interface CacheService {
   StreamHash save(LiveStream liveStream);
   StreamHash save(UUID id, StreamPutRequest liveStream);
   StreamHash updateStreamInfo(UUID id, StreamPatchRequest status);
   StreamHash updateIngressInfo(UUID id, StreamPutRequest status);

   StreamHash getLiveByUserId(UUID userId);

}
