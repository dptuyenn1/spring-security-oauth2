package com.dev.services;

import com.dev.dto.requests.LoginRequest;
import com.dev.dto.requests.RegisterRequest;
import com.dev.dto.responses.AuthResponse;
import com.dev.dto.responses.UserResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(String refreshToken);

    UserResponse me(String username);

    UserResponse register(RegisterRequest request);
}
