package com.infinotive.facility.web.controller;

import com.infinotive.facility.domain.service.AuthService;
import com.infinotive.facility.web.dto.request.LoginRequest;
import com.infinotive.facility.web.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * HTTP endpoints related to authentication.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var resultOpt = authService.login(
                request.getTenantCode(),
                request.getEmail(),
                request.getPassword()
        );

        return resultOpt
                .map(result -> ResponseEntity.ok(
                        new LoginResponse(
                                result.accessToken(),
                                result.tokenType(),
                                result.expiresInSeconds(),
                                result.tenantId(),
                                result.tenantCode(),
                                result.userId(),
                                result.email(),
                                result.role()
                        )
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
