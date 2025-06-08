package com.dev.services.impl;

import com.dev.models.InvalidToken;
import com.dev.repositories.InvalidTokenRepository;
import com.dev.services.InvalidTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvalidTokenServiceImpl implements InvalidTokenService {

    private final InvalidTokenRepository invalidTokenRepository;

    @Override
    public InvalidToken create(InvalidToken token) {
        return invalidTokenRepository.save(token);
    }

    @Override
    public boolean existById(UUID id) {
        return invalidTokenRepository.existsById(id);
    }
}
