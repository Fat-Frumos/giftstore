package com.epam.esm.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails  {
    private int statusCode;
    private String errorMessage;
    private int errorCode;
}
