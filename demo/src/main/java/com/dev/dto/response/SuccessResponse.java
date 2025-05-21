package com.dev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class SuccessResponse extends ResponseEntity<SuccessResponse.Payload> {

    public SuccessResponse(String message, HttpStatusCode status) {
        super(new Payload(message), status);
    }

    public SuccessResponse(String message, Object data, HttpStatusCode status) {
        super(new Payload(message, data), status);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Payload extends Response {
        private Object data;

        public Payload(String message) {
            super(true, message);
        }

        public Payload(String message, Object data) {
            this(message);
            this.data = data;
        }
    }
}
