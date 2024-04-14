package com.demo.patronus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.NOT_FOUND)
public class StreamNotFoundException extends RuntimeException {

    public StreamNotFoundException(String userId) {
        super(String.format("No stream associated with user %s", userId));
    }

}