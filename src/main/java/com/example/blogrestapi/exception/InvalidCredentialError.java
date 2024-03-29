package com.example.blogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentialError extends ResponseStatusException {

    public InvalidCredentialError() {
        super(HttpStatus.UNAUTHORIZED,"Invalid email or password");
    }
}
