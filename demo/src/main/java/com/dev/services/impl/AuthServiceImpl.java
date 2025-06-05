package com.dev.services.impl;

import com.dev.dto.request.LoginRequest;
import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.AuthResponse;
import com.dev.dto.response.UserResponse;
import com.dev.enums.Type;
import com.dev.helpers.Constants;
import com.dev.helpers.Utils;
import com.dev.mappers.UserMapper;
import com.dev.models.InvalidToken;
import com.dev.models.User;
import com.dev.services.AuthService;
import com.dev.services.InvalidTokenService;
import com.dev.services.JwtService;
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
    private final InvalidTokenService invalidTokenService;

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

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.BAD_CREDENTIALS);

        return createAuthResponse(jwtService.generateToken(user, Type.REFRESH),
                jwtService.generateToken(user, Type.ACCESS));
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        JWTClaimsSet claimsSet = jwtService.getClaimsSet(refreshToken);

        User user = userService.findByUsername(claimsSet.getSubject());

        InvalidToken invalidToken = invalidTokenService.findById(UUID.fromString(claimsSet.getJWTID()));

        if (invalidToken != null)
            if (invalidToken.getRevokedAt().before(new Date()))
                throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.TOKEN_REVOKED);

        invalidToken = InvalidToken
                .builder()
                .id(UUID.fromString(claimsSet.getJWTID()))
                .expiredAt(claimsSet.getExpirationTime())
                .revokedAt(new Date())
                .type(Type.REFRESH)
                .user(user)
                .build();

        invalidToken = invalidTokenService.create(invalidToken);

        return createAuthResponse(jwtService.generateToken(user, Type.REFRESH),
                jwtService.generateToken(user, Type.ACCESS));
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
