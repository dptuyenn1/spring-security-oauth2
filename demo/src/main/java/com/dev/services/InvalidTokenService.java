package com.dev.services;

import com.dev.models.InvalidToken;

import java.util.UUID;

public interface InvalidTokenService {

    InvalidToken create(InvalidToken token);

    boolean existById(UUID id);

    InvalidToken getById(UUID id);
}
