package com.dev.demo.dto.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final Object message;
    private final String path;

    public ErrorResponse(Object message, String path) {
        this.message = message;
        this.path = path;
    }
}
