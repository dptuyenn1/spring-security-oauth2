package com.dev.services.impl;

import com.dev.dto.request.LoginRequest;
import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.AuthResponse;
import com.dev.dto.response.UserResponse;
import com.dev.helpers.Constants;
import com.dev.helpers.Utils;
import com.dev.mappers.UserMapper;
import com.dev.models.RefreshToken;
import com.dev.models.User;
import com.dev.services.AuthService;
import com.dev.services.JwtService;
import com.dev.services.RefreshTokenService;
import com.dev.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    private AuthResponse createAuthResponse(String cookieValue, String accessToken) {
        HttpCookie cookie = ResponseCookie
                .from(Constants.CONTROLLERS.AUTH.COOKIE_NAME)
                .value(cookieValue)
                .maxAge(Duration.ofMillis(Constants.JWT.REFRESH_TOKEN.DURATION))
                .path(Utils.getEndpointWithPrefix(Constants.CONTROLLERS.AUTH.COOKIE_PATH))
                .sameSite(SameSiteCookies.STRICT.getValue())
                .secure(true)
                .httpOnly(true)
                .build();

        AuthResponse response = new AuthResponse();

        response.setAccessToken(accessToken);
        response.setRefreshToken(cookie.toString());

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.BAD_CREDENTIALS);

        RefreshToken refreshToken = refreshTokenService.findByUser(user);

        if (refreshToken == null)
            refreshToken = RefreshToken
                    .builder()
                    .value(UUID.randomUUID())
                    .expiredAt(new Date(System.currentTimeMillis() + Constants.JWT.REFRESH_TOKEN.DURATION))
                    .user(user)
                    .build();
        else {
            refreshToken.setValue(UUID.randomUUID());
            refreshToken.setExpiredAt(new Date(System.currentTimeMillis() + Constants.JWT.REFRESH_TOKEN.DURATION));
        }

        refreshToken = refreshTokenService.create(refreshToken);

        return createAuthResponse(refreshToken.getValue().toString(), jwtService.generateToken(user));
    }

    @Override
    public AuthResponse refresh(UUID value) {
        RefreshToken refreshToken = refreshTokenService.findByValue(value);

        refreshToken.setValue(UUID.randomUUID());
        refreshToken.setExpiredAt(new Date(System.currentTimeMillis() + Constants.JWT.REFRESH_TOKEN.DURATION));

        refreshToken = refreshTokenService.create(refreshToken);

        return createAuthResponse(refreshToken.getValue().toString(),
                jwtService.generateToken(refreshToken.getUser()));
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
