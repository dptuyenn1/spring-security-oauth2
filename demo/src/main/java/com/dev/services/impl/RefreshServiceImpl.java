package com.dev.services.impl;

import com.dev.helpers.Constants;
import com.dev.models.RefreshToken;
import com.dev.models.User;
import com.dev.repositories.RefreshTokenRepository;
import com.dev.services.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken create(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByUser(User user) {
        return refreshTokenRepository
                .findByUser(user)
                .filter(refreshToken -> refreshToken.getExpiredAt().after(new Date()))
                .orElse(null);
    }

    @Override
    public RefreshToken findByValue(UUID value) {
        return refreshTokenRepository
                .findByValue(value)
                .filter(refreshToken -> refreshToken.getExpiredAt().after(new Date()))
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format(Constants.EXCEPTION_MESSAGES.NOT_FOUND,
                                "Refresh token with value: " + value)));
    }
}
