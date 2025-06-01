package com.dev.services.impl;

import com.dev.helpers.Constants;
import com.dev.models.User;
import com.dev.services.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String ROLES_CLAIM = "roles";

    @Value("${app.jwt.key}")
    private String key;

    @Override
    public String generateToken(User user) {
        List<String> roles = user
                .getRoles()
                .stream()
                .map(role -> role.getAuthority().name())
                .toList();

        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + Constants.JWT.ACCESS_TOKEN.DURATION);

        JWSHeader header = new JWSHeader
                .Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet claimsSet = new JWTClaimsSet
                .Builder()
                .subject(user.getUsername())
                .issuer(Constants.JWT.ISSUER)
                .issueTime(issuedAt)
                .expirationTime(expiredAt)
                .claim(ROLES_CLAIM, roles)
                .jwtID(UUID.randomUUID().toString())
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
}
