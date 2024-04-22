package com.demo.patronus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenNotFoundException(String message) {
        super(String.format("Token not found", message));
    }
}

