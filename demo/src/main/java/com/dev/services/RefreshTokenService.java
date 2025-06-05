package com.dev.services;

import com.dev.models.RefreshToken;
import com.dev.models.User;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshToken create(RefreshToken refreshToken);

    RefreshToken findByUser(User user);

    RefreshToken findByValue(UUID value);
}
