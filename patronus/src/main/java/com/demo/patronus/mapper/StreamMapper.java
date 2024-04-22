package com.demo.patronus.mapper;

import com.demo.patronus.dto.response.StreamResponse;
import com.demo.patronus.models.LiveStream;

import java.util.List;
import java.util.stream.Collectors;

public class StreamMapper {

    public static StreamResponse mapToStreamResponse(LiveStream liveStream) {
        return new StreamResponse(
                liveStream.getId().toString(),
                liveStream.getCaption(),
                liveStream.getThumbnailUrl(),
                new StreamResponse.UserResponse(liveStream.getUser().getUsername(), liveStream.getUser().getImageUrl()),
                liveStream.isLive(),
                liveStream.getCreatedAt(),
                liveStream.getLikeCount(),
                liveStream.getIngressId(),
                liveStream.getServerUrl(),
                liveStream.getStreamKey()
        );
    }

    public static List<StreamResponse> mapToStreamResponses(List<LiveStream> liveStreams) {
        return liveStreams.stream()
                .map(StreamMapper::mapToStreamResponse)
                .collect(Collectors.toList());
    }



}
