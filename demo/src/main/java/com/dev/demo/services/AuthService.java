package com.dev.demo.services;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthService {

    String login(LoginRequest request) throws JOSEException;

    UserResponse me(String username);

    UserResponse register(RegisterRequest request);
}
