package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ResponseStatus(NOT_FOUND)
public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException(final String message) {
        super(message);
        log.error("An exception: {}", message);
    }
}
