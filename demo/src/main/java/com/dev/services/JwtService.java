package com.dev.services;

import com.dev.models.User;

import javax.crypto.SecretKey;

public interface JwtService {

    String generateToken(User user);

    SecretKey getSecretKey();
}
