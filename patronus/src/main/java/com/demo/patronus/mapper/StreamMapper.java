package com.demo.patronus.mapper;

import com.demo.patronus.dto.response.LiveStreamResponse;
import com.demo.patronus.dto.response.StreamResponse;
import com.demo.patronus.models.LiveStream;
import com.demo.patronus.models.redis.StreamHash;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StreamMapper {
    @Mapping(source = "user", target = "user", qualifiedByName = "toUserResponse")
    StreamResponse mapToStreamResponse(LiveStream liveStream);
    List<StreamResponse> mapToStreamResponses(List<LiveStream> liveStreams);
    LiveStreamResponse mapToLiveStreamResponse(StreamHash hash);
}