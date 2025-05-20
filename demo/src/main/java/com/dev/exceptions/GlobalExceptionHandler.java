package com.dev.exceptions;

import com.dev.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(final UsernameNotFoundException ex, HttpServletRequest request) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError field = (FieldError) error;

            String fieldName = field.getField();
            String errorMessage = field.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        return new ErrorResponse(errors, request.getRequestURI());
    }
}
