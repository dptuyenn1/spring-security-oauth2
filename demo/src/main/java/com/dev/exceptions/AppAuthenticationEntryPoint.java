package com.dev.exceptions;

import com.dev.helpers.Constants;
import com.dev.helpers.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String details = null;

        String message = switch (authException) {
            case InvalidBearerTokenException exception -> Constants.EXCEPTION_MESSAGES.INVALID_TOKEN;
            case InsufficientAuthenticationException exception -> Constants.EXCEPTION_MESSAGES.TOKEN_REQUIRED;
            default -> {
                details = authException.getMessage();
                yield Constants.EXCEPTION_MESSAGES.AUTHENTICATION_FAILED;
            }
        };

        if (details == null)
            Utils.setFilterExceptionResponse(message, response,
                    HttpServletResponse.SC_UNAUTHORIZED, request.getRequestURI());
        else
            Utils.setFilterExceptionResponse(message, response,
                    HttpServletResponse.SC_UNAUTHORIZED, request.getRequestURI(), details);
    }
}
