package com.demo.patronus.mapper;

import com.demo.patronus.dto.response.FollowResponse;
import com.demo.patronus.models.Follow;

import java.util.List;
import java.util.stream.Collectors;

public class FollowMapper {
    public static FollowResponse mapToFollowResponse(Follow follow) {
        return new FollowResponse(
                follow.getFollowing().getId(),
                follow.getFollowing().getUsername(),
                follow.getFollowing().getImageUrl()
        );
    }

    public static List<FollowResponse> mapToFollowsResponse(List<Follow> followers) {
        return followers.stream()
                .map(FollowMapper::mapToFollowResponse)
                .collect(Collectors.toList());
    }
}
