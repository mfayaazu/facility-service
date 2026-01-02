package com.infinotive.facility.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT-related security properties loaded from configuration.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {

    /**
     * Secret key used to sign JWT tokens (HS256).
     * In production this must come from a secure source.
     */
    private String secret;

    /**
     * Issuer value to embed in tokens for validation.
     */
    private String issuer;

    /**
     * Access token validity in seconds.
     */
    private long accessTokenValiditySeconds;
}
