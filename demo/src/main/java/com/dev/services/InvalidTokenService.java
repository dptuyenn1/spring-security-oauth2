package com.dev.services;

import com.dev.models.InvalidToken;

public interface InvalidTokenService {

    InvalidToken create(InvalidToken token);

    InvalidToken findByToken(String token);
}
