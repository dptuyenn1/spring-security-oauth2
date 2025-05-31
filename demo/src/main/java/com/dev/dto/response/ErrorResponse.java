package com.dev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> extends Response<T> {

    private final String path;
    private String details;

    public ErrorResponse(T message, String path) {
        super(false, message);
        this.path = path;
    }

    public ErrorResponse(T message, String details, String path) {
        this(message, path);
        this.details = details;
    }
}
