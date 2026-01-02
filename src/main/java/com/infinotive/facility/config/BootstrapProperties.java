package com.infinotive.facility.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration for initial tenant/admin bootstrap.
 * Values come from application.yml or environment variables.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.bootstrap")
public class BootstrapProperties {

    /**
     * Controls whether bootstrap logic should run on startup.
     */
    private boolean enabled = false;

    private TenantProperties tenant = new TenantProperties();
    private AdminProperties admin = new AdminProperties();

    @Getter
    @Setter
    public static class TenantProperties {
        private String code;
        private String name;
    }

    @Getter
    @Setter
    public static class AdminProperties {
        private String email;
        private String password;
    }
}
