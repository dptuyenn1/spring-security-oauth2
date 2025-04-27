package com.dev.demo.controllers;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.response.AuthResponse;
import com.dev.demo.services.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody LoginRequest request) throws JOSEException {
        AuthResponse authResponse = new AuthResponse();

        authResponse.setAccessToken(authService.login(request));

        return authResponse;
    }
}
