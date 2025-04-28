package com.dev.demo.dto.response;

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
    public static class Payload {
        private final boolean success = true;
        private final String message;
        private final Object data;

        public Payload(String message) {
            this.message = message;
            this.data = null;
        }

        public Payload(String message, Object data) {
            this.message = message;
            this.data = data;
        }
    }
}
