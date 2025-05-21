package com.dev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class SuccessResponse<T> extends ResponseEntity<SuccessResponse.Payload<T>> {

    public SuccessResponse(T message, HttpStatusCode status) {
        super(new Payload<>(message), status);
    }

    public SuccessResponse(T message, Object data, HttpStatusCode status) {
        super(new Payload<>(message, data), status);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Payload<T> extends Response<T> {
        private Object data;

        public Payload(T message) {
            super(true, message);
        }

        public Payload(T message, Object data) {
            this(message);
            this.data = data;
        }
    }
}
