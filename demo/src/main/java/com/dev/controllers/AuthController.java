package com.dev.controllers;

import com.dev.dto.request.LoginRequest;
import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.SuccessResponse;
import com.dev.dto.response.UserResponse;
import com.dev.helpers.Constants;
import com.dev.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.MessageFormat;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirements
    public SuccessResponse<String> login(@RequestBody @Valid LoginRequest request) {
        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Login"), authService.login(request), HttpStatus.OK);
    }

    @GetMapping("/me")
    public SuccessResponse<String> me(Principal principal) {
        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Get user details"), authService.me(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/register")
    @SecurityRequirements
    public SuccessResponse<String> register(@RequestBody @Valid RegisterRequest request) {
        UserResponse response = authService.register(request);

        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Register"), HttpStatus.CREATED);
    }
}
