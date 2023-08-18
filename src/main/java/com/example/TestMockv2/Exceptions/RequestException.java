package com.example.TestMockv2.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestException extends Exception {
    public RequestException(String message) {
        super(message);
    }
}
