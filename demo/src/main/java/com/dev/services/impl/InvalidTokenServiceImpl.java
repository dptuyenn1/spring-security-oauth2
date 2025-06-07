package com.dev.services.impl;

import com.dev.models.InvalidToken;
import com.dev.repositories.InvalidTokenRepository;
import com.dev.services.InvalidTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidTokenServiceImpl implements InvalidTokenService {

    private final InvalidTokenRepository invalidTokenRepository;

    @Override
    public InvalidToken create(InvalidToken token) {
        return invalidTokenRepository.save(token);
    }

    @Override
    public InvalidToken findByToken(String token) {
        return invalidTokenRepository.findByToken(token).orElse(null);
    }
}
