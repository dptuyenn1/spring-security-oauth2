package com.dev.services.impl;

import com.dev.enums.Type;
import com.dev.helpers.Constants;
import com.dev.models.User;
import com.dev.services.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String TYPE_CLAIM = "type";

    @Value("${app.jwt.key}")
    private String key;

    @Override
    public String generateToken(User user, Type type) {
        List<String> roles = user
                .getRoles()
                .stream()
                .map(role -> Constants.JWT.ROLE_PREFIX + role.getAuthority().name())
                .toList();

        JWSHeader header = new JWSHeader
                .Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();

        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + Constants.JWT.DEFAULT_DURATION);

        switch (type) {
            case ACCESS -> expiredAt = new Date(issuedAt.getTime() + Constants.JWT.ACCESS_TOKEN.DURATION);
            case REFRESH -> expiredAt = new Date(issuedAt.getTime() + Constants.JWT.REFRESH_TOKEN.DURATION);
        }

        JWTClaimsSet claimsSet = new JWTClaimsSet
                .Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .issuer(Constants.JWT.ISSUER)
                .issueTime(issuedAt)
                .expirationTime(expiredAt)
                .claim(Constants.JWT.ROLES_CLAIM, roles)
                .claim(Constants.JWT.TYPE_CLAIM, type.name())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        try {
            JWSSigner signer = new MACSigner(key);
            signedJWT.sign(signer);
        } catch (JOSEException exception) {
            throw new JwtException(exception.getMessage());
        }

        return signedJWT.serialize();
    }

    @Override
    public JWTClaimsSet getClaimsSet(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(key);

            if (!signedJWT.verify(verifier))
                throw new JwtException(Constants.EXCEPTION_MESSAGES.INVALID_TOKEN);

            Date expiredAt = signedJWT.getJWTClaimsSet().getExpirationTime();

            if (expiredAt.before(new Date()))
                throw new JwtException(Constants.EXCEPTION_MESSAGES.INVALID_TOKEN);

            return signedJWT.getJWTClaimsSet();
        } catch (JOSEException | ParseException exception) {
            throw new JwtException(exception.getMessage());
        }
    }
}
