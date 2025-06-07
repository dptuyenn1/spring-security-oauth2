package com.dev.configs.impl;

import com.dev.helpers.Constants;
import com.dev.services.UserService;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@NonNullApi
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    private final UserService userService;

    @Override
    /*
    this below annotation help to use jpa (which not be allowed) in audit,
    without this, the audit will cause recursive call => stackoverflow when cannot find any user
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<String> getCurrentAuditor() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .flatMap(username -> Optional
                        .ofNullable(userService.findByUsername(username))
                        .map(user -> String.format("%s %s", user.getLastName(), user.getFirstName())))
                .or(() -> Optional.of(Constants.OTHERS.SYSTEM));
    }
}
