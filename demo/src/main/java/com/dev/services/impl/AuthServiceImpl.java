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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TYPE_CLAIM = "type";
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final InvalidTokenService invalidTokenService;

    private AuthResponse createAuthResponse(String accessToken, String refreshToken) {
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
        User user = userService.getByUsername(request.getUsername());

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.BAD_CREDENTIALS);

        return createAuthResponse(jwtService.generateToken(user, Type.ACCESS),
                jwtService.generateToken(user, Type.REFRESH));
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        JWTClaimsSet claimsSet = jwtService.getClaimsSet(refreshToken);

        User user = userService.getByUsername(claimsSet.getSubject());

        InvalidToken invalidToken = invalidTokenService.findByToken(refreshToken);

        if (invalidToken == null) {
            invalidToken = InvalidToken
                    .builder()
                    .token(refreshToken)
                    .revokedAt(new Date())
                    .expiredAt(claimsSet.getExpirationTime())
                    .type(Type.valueOf(claimsSet.getClaim(TYPE_CLAIM).toString()))
                    .build();

            invalidTokenService.create(invalidToken);
        } else
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.TOKEN_REVOKED);

        return createAuthResponse(jwtService.generateToken(user, Type.ACCESS),
                jwtService.generateToken(user, Type.REFRESH));
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
