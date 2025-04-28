package com.dev.demo.exceptions;

import com.dev.demo.helpers.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String message = "Authentication failed!";

        Utils.setFilterExceptionResponse(message, response,
                HttpServletResponse.SC_UNAUTHORIZED, request.getRequestURI());
    }
}
