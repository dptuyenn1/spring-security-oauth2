package com.dev.demo.services.impl;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.dto.response.AuthResponse;
import com.dev.demo.dto.response.UserResponse;
import com.dev.demo.helpers.Constants;
import com.dev.demo.mappers.UserMapper;
import com.dev.demo.models.User;
import com.dev.demo.services.AuthService;
import com.dev.demo.services.JwtService;
import com.dev.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.BAD_CREDENTIALS);

        AuthResponse response = new AuthResponse();

        response.setAccessToken(jwtService.generateToken(user));

        return response;
    }

    @Override
    public UserResponse me(String username) {
        return userMapper.toUserResponse(userService.findByUsername(username));
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        return userMapper.toUserResponse(userService.create(request));
    }
}
