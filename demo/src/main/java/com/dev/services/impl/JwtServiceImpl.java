package com.dev.services.impl;

import com.dev.helpers.Constants;
import com.dev.models.Role;
import com.dev.models.User;
import com.dev.services.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;

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
            JWSSigner signer = new MACSigner(key);
            signedJWT.sign(signer);
        } catch (JOSEException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return signedJWT.serialize();
    }

    @Override
    public SecretKey getSecretKey() {
        return new SecretKeySpec(key.getBytes(), MacAlgorithm.HS512.getName());
    }
}
