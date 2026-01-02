package com.infinotive.facility.domain.service;

import com.infinotive.facility.config.JwtProperties;
import com.infinotive.facility.domain.entity.User;
import com.infinotive.facility.enums.UserRole;
import com.infinotive.facility.domain.repository.TenantRepository;
import com.infinotive.facility.domain.repository.UserRepository;
import com.infinotive.facility.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Handles authentication and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public Optional<AuthResult> login(String tenantCode, String email, String rawPassword) {
        var tenantOpt = tenantRepository.findByCodeAndActiveTrue(tenantCode);
        if (tenantOpt.isEmpty()) {
            return Optional.empty();
        }

        var tenant = tenantOpt.get();

        var userOpt = userRepository.findByTenant_IdAndEmailAndActiveTrue(tenant.getId(), email);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return Optional.empty();
        }

        String token = jwtService.generateAccessToken(user);

        return Optional.of(new AuthResult(
                token,
                "Bearer",
                jwtProperties.getAccessTokenValiditySeconds(),
                tenant.getId(),
                tenant.getCode(),
                user.getId(),
                user.getEmail(),
                user.getRole()
        ));
    }

    /**
     * Result of a successful authentication.
     */
    public record AuthResult(
            String accessToken,
            String tokenType,
            long expiresInSeconds,
            String tenantId,
            String tenantCode,
            String userId,
            String email,
            UserRole role
    ) {
    }
}
