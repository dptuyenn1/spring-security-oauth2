package com.dev.services;

import com.dev.enums.Type;
import com.dev.models.User;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Date;

public interface JwtService {

    String generateToken(User user, Type type);

    String generateToken(User user, Date expiredAt);

    JWTClaimsSet getClaimsSet(String token);
}
