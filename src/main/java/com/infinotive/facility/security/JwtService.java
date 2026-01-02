package com.infinotive.facility.security;

import com.infinotive.facility.config.JwtProperties;
import com.infinotive.facility.domain.entity.User;
import com.infinotive.facility.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * Responsible for generating JWT access tokens for authenticated users.
 */
@Service
@RequiredArgsConstructor
public class JwtService {
    /**
     * Principal extracted from a valid JWT token.
     */
    public record JwtPrincipal(
            String userId,
            String tenantId,
            String email,
            UserRole role
    ) {
    }

    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(jwtProperties.getAccessTokenValiditySeconds());

        var key = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(user.getId())
                .claim("tenantId", user.getTenant().getId())
                .claim("role", user.getRole().name())
                .claim("email", user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key)
                .compact();
    }

    public JwtPrincipal parseAndValidate(String token) throws JwtException {
        var key = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );

        Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token);

        Claims claims = jws.getPayload();

        String userId = claims.getSubject();
        String tenantId = claims.get("tenantId", String.class);
        String email = claims.get("email", String.class);
        String roleName = claims.get("role", String.class);

        UserRole role = UserRole.valueOf(roleName);

        return new JwtPrincipal(userId, tenantId, email, role);
    }
}



