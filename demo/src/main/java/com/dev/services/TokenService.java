package com.dev.services;

import com.dev.models.Token;

import java.util.UUID;

public interface TokenService {

    Token create(Token token);

    Token getById(UUID id);

    Token findById(UUID id);
}
