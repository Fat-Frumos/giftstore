package com.epam.esm.api.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.NoHandlerFoundException;

public class ResourceNotFoundException extends NoHandlerFoundException {
    public ResourceNotFoundException(
            final String httpMethod,
            final String url,
            final HttpHeaders httpHeaders) {
        super(httpMethod, url, httpHeaders);
    }
}
