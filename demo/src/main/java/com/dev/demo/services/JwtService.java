package com.dev.demo.services;

import com.nimbusds.jose.JOSEException;

import javax.crypto.SecretKey;

public interface JwtService {

    String generateToken(String username) throws JOSEException;

    SecretKey getSecretKey();
}
