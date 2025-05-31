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

    @ExceptionHandler(value = {Exception.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<?> handleException(Exception exception, HttpServletRequest request) {
        if (exception instanceof MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult().getAllErrors().forEach((error) -> {
                FieldError field = (FieldError) error;

                String fieldName = field.getField();
                String errorMessage = field.getDefaultMessage();

                errors.put(fieldName, errorMessage);
            });

            return new ErrorResponse<>(errors, request.getRequestURI());
        }

        return new ErrorResponse<>(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = {NotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handleNotFoundException(final Exception exception, HttpServletRequest request) {
        return new ErrorResponse<>(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse<String> handleException(final DuplicateException exception, HttpServletRequest request) {
        return new ErrorResponse<>(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse<String> handleException(final AuthenticationException exception, HttpServletRequest request) {
        return new ErrorResponse<>(exception.getMessage(), request.getRequestURI());
    }
}
