package com.dev.demo.controllers;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.dto.response.AuthResponse;
import com.dev.demo.dto.response.SuccessResponse;
import com.dev.demo.helpers.Constants;
import com.dev.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.MessageFormat;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public SuccessResponse login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = new AuthResponse();

        authResponse.setAccessToken(authService.login(request));

        return new SuccessResponse(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Login"), authResponse, HttpStatus.OK);
    }

    @GetMapping("/me")
    public SuccessResponse me(Principal principal) {
        return new SuccessResponse(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Get user details"), authService.me(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public SuccessResponse register(@RequestBody RegisterRequest request) {
        authService.register(request);
        
        return new SuccessResponse(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Register"), null, HttpStatus.CREATED);
    }
}
