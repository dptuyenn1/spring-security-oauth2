package com.dev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final boolean success = false;
    private final Object message;
    private final String details;
    private final String path;

    public ErrorResponse(Object message, String path) {
        this.message = message;
        this.path = path;
        this.details = null;
    }

    public ErrorResponse(Object message, String details, String path) {
        this.message = message;
        this.path = path;
        this.details = details;
    }
}
