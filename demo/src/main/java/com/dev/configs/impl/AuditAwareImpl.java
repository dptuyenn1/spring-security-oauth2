package com.dev.configs.impl;

import com.dev.helpers.Constants;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NonNullApi
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken)
            return Optional.of(Constants.AUDIT_AWARE.SYSTEM);

        return Optional
                .ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }
}
