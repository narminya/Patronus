package com.demo.patronus.mapper;

import com.demo.patronus.dto.response.LiveStreamResponse;
import com.demo.patronus.dto.response.StreamResponse;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.redis.StreamHash;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMapper {

    public static StreamResponse mapToStreamResponse(LiveStream liveStream) {
        return new StreamResponse(
                liveStream.getId().toString(),
                liveStream.getCaption(),
                liveStream.getThumbnailUrl(),
                new StreamResponse.UserResponse(liveStream.getUser().getUsername(), liveStream.getUser().getImageUrl()),
                liveStream.getCreatedAt(),
                liveStream.getLikeCount()
        );
    }

    public static List<StreamResponse> mapToStreamResponses(List<LiveStream> liveStreams) {
        return liveStreams.stream()
                .map(StreamMapper::mapToStreamResponse)
                .collect(Collectors.toList());
    }

    public static LiveStreamResponse mapToLiveStreamResponse(StreamHash hash) {
        return new LiveStreamResponse(
                hash.getStreamId().toString(),
                hash.getCaption(),
                hash.getIngressId(),
                hash.getServerUrl(),
                hash.getStreamKey(),
                hash.isChatDelayed(),
                hash.isChatEnabled(),
                hash.isChatFollowersOnly(),
                hash.getUserId().toString(),
                hash.getUsername(),
                hash.getFullName()
        );
    }



}