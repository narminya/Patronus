package com.demo.patronus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {

    @Schema(title = "text to be searched", example = "Spiderman")
    @NotBlank
    private String text;
}