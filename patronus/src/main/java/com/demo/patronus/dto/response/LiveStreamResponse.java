package com.demo.patronus.dto.response;

import com.amazonaws.services.dynamodbv2.xspec.B;

import java.time.LocalDateTime;



public record LiveStreamResponse(String id, String caption, String thumbnailUrl,
                                 String ingressId,
                                 String serverUrl, String streamKey, Boolean chatDelayed, Boolean chatEnabled,
                                 Boolean chatFollowersOnly) {


}