package com.dev.services.impl;

import com.dev.dto.request.LoginRequest;
import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.AuthResponse;
import com.dev.dto.response.UserResponse;
import com.dev.enums.Type;
import com.dev.helpers.Constants;
import com.dev.helpers.Utils;
import com.dev.mappers.UserMapper;
import com.dev.models.Token;
import com.dev.models.User;
import com.dev.services.AuthService;
import com.dev.services.JwtService;
import com.dev.services.TokenService;
import com.dev.services.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
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
    private final TokenService tokenService;

    private AuthResponse createAuthResponse(String refreshToken, String accessToken) {
        HttpCookie cookie = ResponseCookie
                .from(Constants.RESPONSE_COOKIE.NAME)
                .value(refreshToken)
                .maxAge(Duration.ofMillis(Constants.JWT.REFRESH_TOKEN.DURATION))
                .path(Utils.getEndpointWithPrefix(Constants.RESPONSE_COOKIE.PATH))
                .sameSite(SameSiteCookies.STRICT.getValue())
                .secure(true)
                .httpOnly(true)
                .build();

        AuthResponse response = new AuthResponse();

        response.setAccessToken(accessToken);
        response.setRefreshToken(cookie.toString());

        return response;
    }

    private void saveTokens(String accessToken, String refreshToken, User user) {
        String accessTokenId = jwtService.getClaimsSet(accessToken).getJWTID();
        String refreshTokenId = jwtService.getClaimsSet(refreshToken).getJWTID();

        Date accessTokenExpiredAt = jwtService.getClaimsSet(accessToken).getExpirationTime();
        Date refreshTokenExpiredAt = jwtService.getClaimsSet(refreshToken).getExpirationTime();

        Token token = Token
                .builder()
                .id(UUID.fromString(accessTokenId))
                .expiredAt(accessTokenExpiredAt)
                .type(Type.ACCESS)
                .user(user)
                .build();
        token = tokenService.create(token);

        token = Token
                .builder()
                .id(UUID.fromString(refreshTokenId))
                .expiredAt(refreshTokenExpiredAt)
                .type(Type.REFRESH)
                .user(user)
                .build();
        token = tokenService.create(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.getByUsername(request.getUsername());

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.BAD_CREDENTIALS);

        String accessToken = jwtService.generateToken(user, Type.ACCESS);
        String refreshToken = jwtService.generateToken(user, Type.REFRESH);

        saveTokens(accessToken, refreshToken, user);

        return createAuthResponse(refreshToken, accessToken);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        JWTClaimsSet claimsSet = jwtService.getClaimsSet(refreshToken);

        User user = userService.getByUsername(claimsSet.getSubject());

        Token token = tokenService.getById(UUID.fromString(claimsSet.getJWTID()));

        if (token.getRevokedAt() == null) {
            token.setRevokedAt(new Date());

            token = tokenService.create(token);
        } else
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.TOKEN_REVOKED);

        String accessToken = jwtService.generateToken(user, Type.ACCESS);
        refreshToken = jwtService.generateToken(user, Type.REFRESH);

        saveTokens(accessToken, refreshToken, user);

        return createAuthResponse(refreshToken, accessToken);
    }

    @Override
    public UserResponse me(String username) {
        return userMapper.toUserResponse(userService.getByUsername(username));
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        return userMapper.toUserResponse(userService.create(request));
    }
}
