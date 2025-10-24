package com.dev.dto.responses;

import lombok.Getter;

@Getter
public abstract class Response<T> {

    private final boolean success;
    private final T message;

    public Response(boolean success, T message) {
        this.success = success;
        this.message = message;
    }
}
