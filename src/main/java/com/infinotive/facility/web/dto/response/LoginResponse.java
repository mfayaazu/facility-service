package com.infinotive.facility.web.dto.response;

import com.infinotive.facility.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload returned after successful authentication.
 */
@Getter
@AllArgsConstructor
public class LoginResponse {

    private final String accessToken;
    private final String tokenType;
    private final long expiresInSeconds;
    private final String tenantId;
    private final String tenantCode;
    private final String userId;
    private final String email;
    private final UserRole role;
}
