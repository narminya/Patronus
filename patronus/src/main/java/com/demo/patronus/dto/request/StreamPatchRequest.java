package com.demo.patronus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class StreamPatchRequest {

    private String thumbnailUrl;
    private String name;
    private Boolean chatEnabled;
    private Boolean chatFollowersOnly;
    private Boolean chatDelayed;


}


