package com.dev.demo.services;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

public interface JwtService {

    String generateToken(Authentication authentication);

    SecretKey getSecretKey();
}
