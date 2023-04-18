package com.epam.esm.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ErrorResponse.builder()
                        .statusCode(NOT_FOUND.value())
                        .errorMessage(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ErrorResponse.builder()
                        .statusCode(NOT_FOUND.value())
                        .errorMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherExceptions(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .errorMessage(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .errorMessage(ex.getMessage()));
    }
}
