package com.dev.services.impl;

import com.dev.helpers.Constants;
import com.dev.models.Token;
import com.dev.repositories.TokenRepository;
import com.dev.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Token create(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token getById(UUID id) {
        return tokenRepository
                .findById(id)
                .orElseThrow(() -> new BadCredentialsException(Constants.EXCEPTION_MESSAGES.INVALID_TOKEN));
    }

    @Override
    public Token findById(UUID id) {
        return tokenRepository.findById(id).orElse(null);
    }
}
