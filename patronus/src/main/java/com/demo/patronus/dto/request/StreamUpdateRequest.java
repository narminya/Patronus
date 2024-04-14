package com.demo.patronus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamUpdateRequest {

    @NotBlank
    @Schema(description = "The ID of the ingress point for streaming.")
    private String ingressId;

    @NotBlank
    @Schema(description = "The URL of the server for streaming.")
    private String url;

    @NotBlank
    @Schema(description = "The stream key associated with the stream.")
    private String streamKey;


}
