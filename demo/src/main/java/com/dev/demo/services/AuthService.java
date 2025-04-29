package com.dev.demo.services;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.dto.response.AuthResponse;
import com.dev.demo.dto.response.UserResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    UserResponse me(String username);

    UserResponse register(RegisterRequest request);
}
