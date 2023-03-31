package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DaoException extends RuntimeException {

    public DaoException(final Throwable cause) {
        super(cause);
        log.error("An error occurred: {}", cause.getMessage());
    }

    public DaoException(final String message) {
        super(message);
        log.error("An error occurred: {}", message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause.getMessage());
    }
}
