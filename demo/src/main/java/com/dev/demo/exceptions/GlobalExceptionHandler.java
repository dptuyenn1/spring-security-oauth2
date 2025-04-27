package com.dev.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(final Exception ex) {
        final Map<String, String> map = new HashMap<>();

        map.put("message", ex.getMessage());

        return map;
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleException(final NotFoundException ex) {
        final Map<String, String> map = new HashMap<>();

        map.put("message", ex.getMessage());

        return map;
    }
}
