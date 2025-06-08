package com.dev.helpers;

import com.dev.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static void setFilterExceptionResponse(String message, HttpServletResponse response,
                                                  int statusCode, String requestURI) throws IOException {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(message, requestURI);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(outputStream, errorResponse);
    }

    public static void setFilterExceptionResponse(String message, HttpServletResponse response, int statusCode,
                                                  String requestURI, String details) throws IOException {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(message, details, requestURI);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(outputStream, errorResponse);
    }

    public static String[] getEndpointsWithPrefix(String... endpoints) {
        List<StringBuilder> stringBuilders = new ArrayList<>();

        if (endpoints.length == 1)
            stringBuilders.add(new StringBuilder(endpoints[0]));
        else
            for (String endpoint : endpoints)
                stringBuilders.add(new StringBuilder(endpoint));

        return stringBuilders
                .stream()
                .map(item -> item.insert(0, Constants.API.PATH_PREFIX).toString())
                .toArray(String[]::new);
    }

    public static String getEndpointWithPrefix(String endpoint) {
        return getEndpointsWithPrefix(endpoint)[0];
    }

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.OTHERS.DATETIME_FORMAT_PATTERN);

        return simpleDateFormat.format(date);
    }
}
