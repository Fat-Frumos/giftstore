package com.epam.esm.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class UserNotFoundException
        extends RuntimeException {
    public UserNotFoundException(
            final String message) {
        super(message);
    }
}
