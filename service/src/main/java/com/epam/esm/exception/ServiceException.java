package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceException extends RuntimeException {

    public ServiceException(
            final Throwable e) {
        super(e.getMessage());
        log.error("{}",
                e.getMessage());
    }

    public ServiceException(
            final String message,
            final Throwable e) {
        super(message, e);
        log.error("{}{}",
                message, e.getMessage());
    }

    public ServiceException(final String message) {
        super(message);
    }
}
