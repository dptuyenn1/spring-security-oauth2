package com.dev.dto.response;

import lombok.Getter;

@Getter
public abstract class Response {

    private final boolean success;
    private final Object message;

    public Response(boolean success, Object message) {
        this.success = success;
        this.message = message;
    }
}
