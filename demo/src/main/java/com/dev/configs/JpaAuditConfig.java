package com.dev.configs;

import com.dev.configs.impl.AuditAwareImpl;
import com.dev.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class JpaAuditConfig {

    private final UserRepository userRepository;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditAwareImpl(userRepository);
    }
}
