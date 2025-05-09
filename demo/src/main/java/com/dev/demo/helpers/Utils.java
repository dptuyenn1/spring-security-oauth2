package com.dev.demo.helpers;

import com.dev.demo.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.OutputStream;

public class Utils {

    public static void setFilterExceptionResponse(String message, HttpServletResponse response,
                                                  int statusCode, String requestURI) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(message, requestURI);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(outputStream, errorResponse);
    }

    public static void setFilterExceptionResponse(String message, HttpServletResponse response, int statusCode,
                                                  String requestURI, String details) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(message, details, requestURI);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(outputStream, errorResponse);
    }

    public static void addEndpointsPrefix(StringBuilder[]... endpointsList) {
        String prefix = String.format(Constants.API.PATH_PREFIX_FORMAT,
                Constants.API.PREFIX, Constants.API.VERSION);

        if (endpointsList.length == 1) {
            for (StringBuilder endpoint : endpointsList[0]) {
                endpoint.insert(0, prefix);
            }

            return;
        }

        for (StringBuilder[] endpoints : endpointsList) {
            for (StringBuilder endpoint : endpoints) {
                endpoint.insert(0, prefix);
            }
        }
    }
}
