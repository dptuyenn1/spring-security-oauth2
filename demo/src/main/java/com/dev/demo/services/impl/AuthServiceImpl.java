package com.dev.demo.services.impl;

import com.dev.demo.dto.request.LoginRequest;
import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.dto.response.AuthResponse;
import com.dev.demo.dto.response.UserResponse;
import com.dev.demo.mappers.Mapper;
import com.dev.demo.models.User;
import com.dev.demo.services.AuthService;
import com.dev.demo.services.JwtService;
import com.dev.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final Mapper<User, UserResponse> mapper;

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        AuthResponse response = new AuthResponse();

        response.setAccessToken(jwtService.generateToken(authentication));

        return response;
    }

    @Override
    public UserResponse me(String username) {
        return mapper.mapFromModelToDTO(userService.findByUsername(username));
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        return mapper.mapFromModelToDTO(userService.create(request));
    }
}
