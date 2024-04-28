package com.demo.patronus.services.impl;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.redis.StreamHash;
import com.demo.patronus.repository.RedisRepository;
import com.demo.patronus.services.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CacheServiceImpl implements CacheService {
    private final RedisRepository redisRepository;

    @Override
    public StreamHash save(LiveStream liveStream) {
        var streamHash = StreamHash.builder()
                .id(liveStream.getId())
                .caption(liveStream.getCaption())
                .description(liveStream.getDescription())
                .userId(liveStream.getUser().getId())
                .username(liveStream.getUser().getUsername())
                .fullName(liveStream.getUser().getName())
                .build();

        redisRepository.findByUserId(liveStream.getUser().getId())
                .ifPresent(redisRepository::delete);
        redisRepository.save(streamHash);
        return streamHash;
    }


    @Override
    public StreamHash save(UUID id, StreamPutRequest liveStream) {
        StreamHash stream = redisRepository.findById(id).orElseThrow();
        stream.setStreamKey(liveStream.getStreamKey());
        stream.setServerUrl(liveStream.getUrl());
        stream.setIngressId(liveStream.getIngressId());
        return redisRepository.save(stream);
    }

    @Override
    public List<StreamHash> getAllLiveStreams() {
        return (List<StreamHash>)redisRepository.findAll();
    }


    @Override
    public StreamHash updateStreamInfo(UUID id, StreamPatchRequest status) {
        StreamHash stream = redisRepository.findById(id).orElseThrow();
        stream.setThumbnailUrl(status.getThumbnailUrl());
        stream.setChatDelayed(status.getChatDelayed());
        stream.setChatEnabled(status.getChatEnabled());
        stream.setChatFollowersOnly(status.getChatFollowersOnly());
        return redisRepository.save(stream);
    }

    @Override
    public StreamHash updateIngressInfo(UUID id, StreamPutRequest status) {
        StreamHash stream = redisRepository.findById(id).orElseThrow();
        stream.setIngressId(status.getIngressId());
        stream.setStreamKey(status.getStreamKey());
        stream.setServerUrl(status.getUrl());
        return redisRepository.save(stream);
    }


    @Override
    public StreamHash getLiveByUserId(UUID userId) {
         return redisRepository.findByUserId(userId).orElseThrow();
    }
    @Override
    public void removeStream(UUID streamId) {
         redisRepository.deleteById(streamId);
    }

}
