package com.dev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class SuccessResponse<T> extends ResponseEntity<SuccessResponse.Payload<T>> {

    public SuccessResponse(String message, HttpStatusCode status) {
        super(new Payload<>(message), status);
    }

    public SuccessResponse(String message, T data, HttpStatusCode status) {
        super(new Payload<>(message, data), status);
    }

    public SuccessResponse(String message, T data, MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(new Payload<>(message, data), headers, status);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Payload<T> extends Response<String> {
        private T data;

        public Payload(String message) {
            super(true, message);
        }

        public Payload(String message, T data) {
            this(message);
            this.data = data;
        }
    }
}
