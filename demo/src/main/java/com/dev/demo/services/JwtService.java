package com.dev.demo.services;

import com.dev.demo.models.User;

import javax.crypto.SecretKey;

public interface JwtService {

    String generateToken(User user);

    SecretKey getSecretKey();
}
