package com.infinotive.facility.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Email notification configuration.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.notifications.email")
public class NotificationEmailProperties {

    /**
     * Controls whether email notifications are actually sent.
     */
    private boolean enabled = false;

    /**
     * Sender address used in outgoing mails.
     */
    private String fromAddress;

    /**
     * Default admin recipient (can be refined per-tenant later).
     */
    private String toAdminAddress;
}
