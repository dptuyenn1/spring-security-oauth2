package com.dev.services;

import com.dev.models.User;

public interface JwtService {

    String generateToken(User user);
}
