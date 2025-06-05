package com.dev.configs.impl;

import com.dev.helpers.Constants;
import com.dev.repositories.UserRepository;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NonNullApi
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    private final UserRepository userRepository;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .flatMap(username -> userRepository
                        .findByUsername(username)
                        .map(user -> String.format("%s %s", user.getLastName(), user.getFirstName())))
                .or(() -> Optional.of(Constants.OTHERS.SYSTEM));
    }
}
