package com.demo.patronus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StreamPatchRequest {

    private String thumbnailUrl;
    private String name;
    private Boolean chatEnabled;
    private Boolean chatFollowersOnly;
    private Boolean chatDelayed;

}


