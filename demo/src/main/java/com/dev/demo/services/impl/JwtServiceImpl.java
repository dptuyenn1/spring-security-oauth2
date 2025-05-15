package com.dev.demo.services.impl;

import com.dev.demo.helpers.Constants;
import com.dev.demo.models.Role;
import com.dev.demo.models.User;
import com.dev.demo.services.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String KEY = "vlywKvNRfV2EtFyJAY7ZOtNZdK2Na8wAAydvd9iZZkqswoqoadtLsrfnUjvU86ve";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public String generateToken(User user) {
        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + Constants.JWT.DURATION);

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
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        try {
            JWSSigner signer = new MACSigner(KEY);
            signedJWT.sign(signer);
        } catch (JOSEException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return signedJWT.serialize();
    }

    @Override
    public SecretKey getSecretKey() {
        return new SecretKeySpec(KEY.getBytes(), MacAlgorithm.HS512.getName());
    }
}
