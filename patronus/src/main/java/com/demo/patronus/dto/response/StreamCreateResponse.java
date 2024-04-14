package com.demo.patronus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamCreateResponse {
    private String caption;
    private String thumbnailUrl;
}
