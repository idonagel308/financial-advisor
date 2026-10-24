package com.idonagel.financialadvisor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
/**
 * Turns invalid-input errors from any controller into a clear 400 response.
 * <p>
 * When a calculator rejects a value (for example a score outside 1 to 3), it throws an
 * {@link IllegalArgumentException}. Instead of a 500 error, the client gets 400 Bad Request
 * with a body like {@code {"error": "<message>"}}.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidInput(IllegalArgumentException exception) {
        return Map.of("error", exception.getMessage());
    }
}
