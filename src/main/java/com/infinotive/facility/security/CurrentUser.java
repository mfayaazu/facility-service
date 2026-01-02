package com.infinotive.facility.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Provides access to the authenticated JWT principal.
 */
public final class CurrentUser {

    private CurrentUser() {}

    public static JwtService.JwtPrincipal get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof JwtService.JwtPrincipal principal)) {
            throw new IllegalStateException("No authenticated principal available");
        }

        return principal;
    }
}
