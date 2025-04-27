package com.dev.demo.services.impl;

import com.dev.demo.services.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final int DAYS_OF_WEEK = 7;
    private static final int HOURS_OF_DAY = 24;
    private static final int MINUTES_OF_HOUR = 60;
    private static final int SECONDS_OF_MINUTE = 60;
    private static final int MILLISECONDS_OF_SECOND = 1000;
    private static final int EXPIRATION_TIME = DAYS_OF_WEEK * HOURS_OF_DAY * MINUTES_OF_HOUR
            * SECONDS_OF_MINUTE * MILLISECONDS_OF_SECOND;

    private static final String KEY = "vlywKvNRfV2EtFyJAY7ZOtNZdK2Na8wAAydvd9iZZkqswoqoadtLsrfnUjvU86ve";
    private static final String ALGORITHM = "AES";
    private static final String ISSUER = "ADMIN";

    @Override
    public String generateToken(String username) throws JOSEException {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        JWSSigner signer = new MACSigner(KEY);

        JWTClaimsSet claimsSet = new JWTClaimsSet
                .Builder()
                .subject(username)
                .issuer(ISSUER)
                .issueTime(issuedAt)
                .expirationTime(expiredAt)
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    @Override
    public SecretKey getSecretKey() {
        return new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    }
}
