package com.epam.esm.exception;

/**
 * Exception thrown when a requested tag is not found in the system.
 */
public class TagNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code TagNotFoundException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public TagNotFoundException(final String message) {
        super(message);
    }
}