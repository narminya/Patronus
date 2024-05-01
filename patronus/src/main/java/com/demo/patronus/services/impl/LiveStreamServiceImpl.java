package com.demo.patronus.services.impl;

import com.demo.patronus.dto.request.StreamPatchRequest;
import com.demo.patronus.dto.request.StreamPutRequest;
import com.demo.patronus.models.jpa.LiveStream;
import com.demo.patronus.models.redis.StreamHash;
import com.demo.patronus.repository.RedisRepository;
import com.demo.patronus.services.LiveStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LiveStreamServiceImpl implements LiveStreamService {
    private final RedisRepository redisRepository;
    @Override
    public StreamHash save(LiveStream liveStream) {
        var streamHash = StreamHash.builder()
                .streamId(liveStream.getId())
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
        StreamHash stream = redisRepository.findByUserId(id).orElseThrow();
        stream.setChatDelayed(status.getChatDelayed());
        stream.setChatEnabled(status.getChatEnabled());
        stream.setChatFollowersOnly(status.getChatFollowersOnly());
        return redisRepository.save(stream);
    }

    @Override
    public StreamHash updateIngressInfo(UUID id, StreamPutRequest status) {
        StreamHash stream = redisRepository.findByUserId(id).orElseThrow();
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
    public void removeStream(UUID userId) {
        var liveStream = redisRepository.findByUserId(userId)
                .orElseThrow();
        redisRepository.deleteById(liveStream.getStreamId());
    }
    @Override
    public StreamHash getById(UUID streamId) {
        return redisRepository.findById(streamId).orElseThrow();
    }



}
