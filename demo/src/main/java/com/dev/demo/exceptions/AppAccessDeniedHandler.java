package com.dev.demo.exceptions;

import com.dev.demo.helpers.Constants;
import com.dev.demo.helpers.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String message = Constants.EXCEPTION_MESSAGES.ACCESS_DENIED;

        Utils.setFilterExceptionResponse(message, response,
                HttpServletResponse.SC_FORBIDDEN, request.getRequestURI());
    }
}
