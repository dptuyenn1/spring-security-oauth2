package com.dev.controllers;

import com.dev.dto.requests.LoginRequest;
import com.dev.dto.requests.RegisterRequest;
import com.dev.dto.responses.AuthResponse;
import com.dev.dto.responses.SuccessResponse;
import com.dev.dto.responses.UserResponse;
import com.dev.helpers.Constants;
import com.dev.services.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.MessageFormat;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirements
    public SuccessResponse<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse response = authService.login(request);

        MultiValueMap<String, String> headers = new HttpHeaders();

        headers.set(HttpHeaders.SET_COOKIE, response.getRefreshToken());

        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Login"), response, headers, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    @SecurityRequirements
    public SuccessResponse<AuthResponse> refresh(
            @Parameter(hidden = true)
            @CookieValue(value = Constants.RESPONSE_COOKIE.NAME) String refreshToken) {
        AuthResponse response = authService.refresh(refreshToken);

        MultiValueMap<String, String> headers = new HttpHeaders();

        headers.set(HttpHeaders.SET_COOKIE, response.getRefreshToken());

        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Refresh token"), response, headers, HttpStatus.OK);
    }

    @GetMapping("/me")
    public SuccessResponse<UserResponse> me(Principal principal) {
        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Get user details"), authService.me(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/register")
    @SecurityRequirements
    public SuccessResponse<Void> register(@RequestBody @Valid RegisterRequest request) {
        UserResponse response = authService.register(request);

        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Register"), HttpStatus.CREATED);
    }
}
