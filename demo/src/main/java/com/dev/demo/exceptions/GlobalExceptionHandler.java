package com.dev.demo.exceptions;

import com.dev.demo.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Exception ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(final NotFoundException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleException(final DuplicateException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final AuthenticationException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getRequestURI());
    }
}
