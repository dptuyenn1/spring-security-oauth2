package com.dev.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mock")
public class MockController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from protected endpoint!";
    }
}
