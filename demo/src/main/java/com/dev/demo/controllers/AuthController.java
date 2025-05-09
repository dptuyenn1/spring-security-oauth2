package com.dev.demo.controllers;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.dto.response.SuccessResponse;
import com.dev.demo.dto.response.UserResponse;
import com.dev.demo.helpers.Constants;
import com.dev.demo.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
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
    public SuccessResponse login(@RequestBody LoginRequest request) {
        return new SuccessResponse(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Login"), authService.login(request), HttpStatus.OK);
    }

    @GetMapping("/me")
    public SuccessResponse me(Principal principal) {
        return new SuccessResponse(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Get user details"), authService.me(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/register")
    @SecurityRequirements
    public SuccessResponse register(@RequestBody RegisterRequest request) {
        UserResponse response = authService.register(request);

        return new SuccessResponse(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Register"), HttpStatus.CREATED);
    }
}
