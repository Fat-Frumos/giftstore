package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(final Throwable e) {
        super(e);
        log.error("TagNotFoundException", e);
    }
}
