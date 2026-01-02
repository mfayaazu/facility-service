package com.infinotive.facility.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

/**
 * Returns RFC 9457-style problem+json for unauthorized requests (401).
 */
@Component
public class ProblemAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Authentication token is missing or invalid"
        );
        pd.setTitle("Unauthorized");
        pd.setType(URI.create("https://example.com/errors/unauthorized"));
        pd.setProperty("code", "UNAUTHORIZED");
        pd.setProperty("instance", request.getRequestURI());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/problem+json;charset=UTF-8");

        // simple manual JSON write (no ObjectMapper wiring)
        String body = """
                {
                  "type": "%s",
                  "title": "%s",
                  "status": %d,
                  "detail": "%s",
                  "code": "%s",
                  "instance": "%s"
                }
                """.formatted(
                pd.getType(),
                pd.getTitle(),
                pd.getStatus(),
                pd.getDetail(),
                pd.getProperties().get("code"),
                pd.getProperties().get("instance")
        );

        response.getWriter().write(body);
    }
}
