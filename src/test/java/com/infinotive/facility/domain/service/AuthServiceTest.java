package com.infinotive.facility.domain.service;

import com.infinotive.facility.config.JwtProperties;
import com.infinotive.facility.domain.entity.Tenant;
import com.infinotive.facility.domain.entity.User;
import com.infinotive.facility.enums.UserRole;
import com.infinotive.facility.domain.repository.TenantRepository;
import com.infinotive.facility.domain.repository.UserRepository;
import com.infinotive.facility.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private TenantRepository tenantRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private JwtProperties jwtProperties;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        tenantRepository = mock(TenantRepository.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtProperties = new JwtProperties();
        jwtProperties.setIssuer("facility-service");
        jwtProperties.setSecret("test-secret-test-secret-test-secret-1234");
        jwtProperties.setAccessTokenValiditySeconds(900);

        jwtService = new JwtService(jwtProperties);

        authService = new AuthService(
                tenantRepository,
                userRepository,
                passwordEncoder,
                jwtService,
                jwtProperties
        );
    }

    @Test
    void login_returnsToken_whenCredentialsAreValid() {
        // given
        Tenant tenant = new Tenant("demo", "Demo Tenant");
        when(tenantRepository.findByCodeAndActiveTrue("demo"))
                .thenReturn(Optional.of(tenant));

        User user = new User(tenant, "admin@example.com", "hash", UserRole.ADMIN);
        when(userRepository.findByTenant_IdAndEmailAndActiveTrue(tenant.getId(), "admin@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("Admin123!", "hash"))
                .thenReturn(true);

        // when
        var resultOpt = authService.login("demo", "admin@example.com", "Admin123!");

        // then
        assertThat(resultOpt).isPresent();
        var result = resultOpt.get();
        assertThat(result.accessToken()).isNotBlank();
        assertThat(result.email()).isEqualTo("admin@example.com");
        assertThat(result.tenantCode()).isEqualTo("demo");
        assertThat(result.role()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void login_returnsEmpty_whenPasswordIsWrong() {
        Tenant tenant = new Tenant("demo", "Demo Tenant");
        when(tenantRepository.findByCodeAndActiveTrue("demo"))
                .thenReturn(Optional.of(tenant));

        User user = new User(tenant, "admin@example.com", "hash", UserRole.ADMIN);
        when(userRepository.findByTenant_IdAndEmailAndActiveTrue(tenant.getId(), "admin@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "hash"))
                .thenReturn(false);

        var resultOpt = authService.login("demo", "admin@example.com", "wrong");

        assertThat(resultOpt).isEmpty();
    }
}
