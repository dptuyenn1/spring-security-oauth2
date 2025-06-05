package com.dev.services;

import com.dev.enums.Type;
import com.dev.models.User;
import com.nimbusds.jwt.JWTClaimsSet;

public interface JwtService {

    String generateToken(User user, Type type);

    JWTClaimsSet getClaimsSet(String token);
}
