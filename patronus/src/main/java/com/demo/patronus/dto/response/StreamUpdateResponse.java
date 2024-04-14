package com.demo.patronus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamUpdateResponse {
    private String serverUrl;
    private String streamKey;
}
