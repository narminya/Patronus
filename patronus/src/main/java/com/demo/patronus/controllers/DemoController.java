package com.demo.patronus.controllers;

import com.demo.patronus.dto.request.SignupRequest;
import com.demo.patronus.dto.response.SignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class DemoController {
    @PostMapping
    public ResponseEntity<SignupResponse> get(@RequestBody @Valid SignupRequest request) {

        SignupResponse response = SignupResponse.builder()
                .message("Sign Up Complete!")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SignupResponse handlePasswordValidationException(MethodArgumentNotValidException e) {

        return SignupResponse.builder()
                .message(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()))
                .timestamp(Instant.now())
                .build();

    }

}