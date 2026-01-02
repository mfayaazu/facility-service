package com.infinotive.facility.web.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for user login.
 */
@Getter
@Setter
public class LoginRequest {

    private String tenantCode;
    private String email;
    private String password;
}
