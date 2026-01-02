package com.infinotive.facility.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple endpoint to verify authentication + service health.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/ping")
    public String ping(Authentication auth) {
        System.out.println(auth.getPrincipal());
        return "ok";
    }
}
