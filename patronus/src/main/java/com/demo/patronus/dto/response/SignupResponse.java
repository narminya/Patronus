package com.demo.patronus.dto.response;

import com.demo.patronus.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {

    private String message;
    private Instant timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;
}
