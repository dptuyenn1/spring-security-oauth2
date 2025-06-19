package com.dev.services.impl;

import com.dev.dto.cache.InvalidToken;
import com.dev.dto.request.LoginRequest;
import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.AuthResponse;
import com.dev.dto.response.UserResponse;
import com.dev.enums.Type;
import com.dev.helpers.Constants;
import com.dev.helpers.Utils;
import com.dev.mappers.UserMapper;
import com.dev.models.User;
import com.dev.services.AuthService;
import com.dev.services.JwtService;
import com.dev.services.RedisService;
import com.dev.services.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String KEY = "refreshTokens";

    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService<InvalidToken> redisService;

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

        UUID id = UUID.fromString(claimsSet.getJWTID());

        Type type = Type.valueOf(claimsSet.getClaim(Constants.JWT.TYPE_CLAIM).toString());

        Date expiredAt = claimsSet.getExpirationTime();

        if (type != Type.REFRESH)
            throw new BadCredentialsException(Constants.EXCEPTION_MESSAGES.INVALID_TOKEN);

        InvalidToken invalidToken = redisService.get(KEY, id.toString());

        if (invalidToken == null) {
            long duration = expiredAt.getTime() - System.currentTimeMillis();

            invalidToken = InvalidToken
                    .builder()
                    .id(id)
                    .revokedAt(new Date())
                    .type(type)
                    .build();

            redisService.put(KEY, id.toString(), invalidToken, duration);
        } else
            throw new BadCredentialsException(MessageFormat.format(
                    Constants.EXCEPTION_MESSAGES.TOKEN_REVOKED, Utils.dateToString(invalidToken.getRevokedAt())));

        return createAuthResponse(jwtService.generateToken(user, Type.ACCESS),
                jwtService.generateToken(user, expiredAt));
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
