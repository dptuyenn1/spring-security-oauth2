package com.dev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends Response {

    private final String path;
    private String details;

    public ErrorResponse(Object message, String path) {
        super(false, message);
        this.path = path;
    }

    public ErrorResponse(Object message, String details, String path) {
        this(message, path);
        this.details = details;
    }
}
