package com.demo.patronus.mapper;

import com.demo.patronus.dto.response.StreamResponse;
import com.demo.patronus.models.LiveStream;
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

//    public static StreamResponse mapToStreamResponse(LiveStream liveStream) {
//        return new StreamResponse(
//                liveStream.getId().toString(),
//                liveStream.getCaption(),
//                liveStream.getThumbnailUrl(),
//                new StreamResponse.UserResponse(liveStream.getUser().getUsername(), liveStream.getUser().getImageUrl()),
//                liveStream.getCreatedAt(),
//                liveStream.getLikeCount()
//        );
//    }
//
//    public static List<StreamResponse> mapToStreamResponses(List<LiveStream> liveStreams) {
//        return liveStreams.stream()
//                .map(StreamMapper::mapToStreamResponse)
//                .collect(Collectors.toList());
//    }
//
//    public static LiveStreamResponse mapToLiveStreamResponse(StreamHash hash) {
//        return new LiveStreamResponse(
//                hash.getStreamId().toString(),
//                hash.getCaption(),
//                hash.getIngressId(),
//                hash.getServerUrl(),
//                hash.getStreamKey(),
//                hash.isChatDelayed(),
//                hash.isChatEnabled(),
//                hash.isChatFollowersOnly(),
//                hash.getUserId().toString(),
//                hash.getUsername(),
//                hash.getFullName()
//        );
//    }


}