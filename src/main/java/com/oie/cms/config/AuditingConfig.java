package com.oie.cms.config;

import com.oie.cms.auth.AuthUser;
import com.oie.cms.entities.employee.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    @Bean
    AuditorAware<Long> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal() instanceof AuthUser user
                ? user.getEmployee().getId() : null);
    }
}
