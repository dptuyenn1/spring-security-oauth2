package com.dev.exceptions;

import com.dev.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleException(final Exception ex, HttpServletRequest request) {
        ErrorResponse<?> errorResponse = new ErrorResponse<>(ex.getMessage(), request.getRequestURI());

        ResponseEntity.BodyBuilder bodyBuilder = switch (ex) {
            case NotFoundException exception -> ResponseEntity.status(HttpStatus.NOT_FOUND);
            case UsernameNotFoundException exception -> ResponseEntity.status(HttpStatus.NOT_FOUND);
            case DuplicateException exception -> ResponseEntity.status(HttpStatus.CONFLICT);
            case AuthenticationException exception -> ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            case MethodArgumentNotValidException exception -> {
                Map<String, String> errors = new HashMap<>();

                exception.getBindingResult().getAllErrors().forEach((error) -> {
                    FieldError field = (FieldError) error;

                    String fieldName = field.getField();
                    String errorMessage = field.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

                errorResponse = new ErrorResponse<>(errors, request.getRequestURI());

                yield ResponseEntity.status(HttpStatus.BAD_REQUEST);
            }
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST);
        };

        return bodyBuilder.body(errorResponse);
    }
}
