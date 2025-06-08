package com.dev.controllers;

import com.dev.dto.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/hello")
    public SuccessResponse<String> hello() {
        return new SuccessResponse<>("Hello from protected endpoint!", HttpStatus.OK);
    }
}
