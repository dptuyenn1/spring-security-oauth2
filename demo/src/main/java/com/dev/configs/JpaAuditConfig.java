package com.dev.configs;

import com.dev.configs.impl.AuditAwareImpl;
import com.dev.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider(UserService userService) {
        return new AuditAwareImpl(userService);
    }
}
