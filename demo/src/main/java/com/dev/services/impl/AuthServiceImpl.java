package com.dev.services.impl;

import com.dev.dto.request.LoginRequest;
import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.AuthResponse;
import com.dev.dto.response.UserResponse;
import com.dev.helpers.Constants;
import com.dev.mappers.UserMapper;
import com.dev.models.User;
import com.dev.services.AuthService;
import com.dev.services.JwtService;
import com.dev.services.UserService;
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
