package com.idonagel.financialadvisor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

/**
 * Turns client mistakes into a clear 400 response, from any controller.
 * <p>
 * Every error has the same shape: {@code {"error": "<message>"}}.
 * <ul>
 *   <li>Invalid value (for example a score outside 1 to 3): a calculator throws
 *       {@link IllegalArgumentException}, and its message is returned.</li>
 *   <li>Missing field or broken JSON: Spring throws {@link HttpMessageNotReadableException},
 *       and a fixed message is returned, so no server details reach the client.</li>
 * </ul>
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidInput(IllegalArgumentException exception) {
        return Map.of("error", Objects.requireNonNullElse(exception.getMessage(), "Invalid input"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUnreadableBody() {
        return Map.of("error", "Request body is missing a field or is not valid JSON");
    }
}
