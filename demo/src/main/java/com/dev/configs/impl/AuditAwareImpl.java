package com.dev.configs.impl;

import com.dev.helpers.Constants;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

@NonNullApi
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken)
            return Optional
                    .of(authentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getName);

        return Optional.of(Constants.AUDIT_AWARE.SYSTEM);
    }
}
