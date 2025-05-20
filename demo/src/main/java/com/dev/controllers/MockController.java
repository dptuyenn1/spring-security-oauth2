package com.dev.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
@CrossOrigin
public class MockController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from protected endpoint!";
    }
}
