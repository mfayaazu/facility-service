package com.infinotive.facility.config;

import com.infinotive.facility.domain.entity.Tenant;
import com.infinotive.facility.domain.entity.User;
import com.infinotive.facility.enums.UserRole;
import com.infinotive.facility.domain.repository.TenantRepository;
import com.infinotive.facility.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Initializes a default tenant and admin user for development.
 * In production, values should be provided via secure configuration.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BootstrapProperties bootstrapProperties;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!bootstrapProperties.isEnabled()) {
            log.info("Bootstrap is disabled. Skipping data initialization.");
            return;
        }

        var tenantCode = bootstrapProperties.getTenant().getCode();
        var tenantName = bootstrapProperties.getTenant().getName();
        var adminEmail = bootstrapProperties.getAdmin().getEmail();
        var adminPassword = bootstrapProperties.getAdmin().getPassword();

        if (tenantCode == null || tenantName == null || adminEmail == null || adminPassword == null) {
            log.warn("Bootstrap properties incomplete. Skipping data initialization.");
            return;
        }

        Tenant tenant = tenantRepository
                .findByCodeAndActiveTrue(tenantCode)
                .orElseGet(() -> {
                    log.info("Creating bootstrap tenant with code='{}'", tenantCode);
                    return tenantRepository.save(new Tenant(tenantCode, tenantName));
                });

        Optional<User> existingAdmin =
                userRepository.findByTenant_IdAndEmailAndActiveTrue(tenant.getId(), adminEmail);

        if (existingAdmin.isPresent()) {
            log.info("Admin user '{}' already exists for tenant '{}'. Skipping admin creation.",
                    adminEmail, tenantCode);
            return;
        }

        String passwordHash = passwordEncoder.encode(adminPassword);

        User adminUser = new User(
                tenant,
                adminEmail,
                passwordHash,
                UserRole.ADMIN
        );

        userRepository.save(adminUser);

        log.info("Bootstrap admin user '{}' created for tenant '{}'.", adminEmail, tenantCode);
    }
}
