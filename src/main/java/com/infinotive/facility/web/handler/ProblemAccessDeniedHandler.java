package com.infinotive.facility.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

/**
 * Returns RFC 9457-style problem+json for forbidden requests (403).
 */
@Component
public class ProblemAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "You do not have permission to access this resource"
        );
        pd.setTitle("Forbidden");
        pd.setType(URI.create("https://example.com/errors/forbidden"));
        pd.setProperty("code", "FORBIDDEN");
        pd.setProperty("instance", request.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/problem+json;charset=UTF-8");

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
