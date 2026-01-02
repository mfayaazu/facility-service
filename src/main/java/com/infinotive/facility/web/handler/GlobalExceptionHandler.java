package com.infinotive.facility.web.handler;

import com.infinotive.facility.domain.exception.ResourceNotFoundException;
import com.infinotive.facility.domain.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

/**
 * Global exception mapping to RFC 9457 problem+json responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Resource not found");
        pd.setType(URI.create("https://example.com/errors/resource-not-found"));
        pd.setProperty("code", "RESOURCE_NOT_FOUND");
        pd.setProperty("instance", request.getRequestURI());
        return pd;
    }

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleValidation(ValidationException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Validation error");
        pd.setType(URI.create("https://example.com/errors/validation-error"));
        pd.setProperty("code", "VALIDATION_ERROR");
        pd.setProperty("instance", request.getRequestURI());
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Bad request");
        pd.setType(URI.create("https://example.com/errors/bad-request"));
        pd.setProperty("code", "BAD_REQUEST");
        pd.setProperty("instance", request.getRequestURI());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");
        pd.setTitle("Internal server error");
        pd.setType(URI.create("https://example.com/errors/internal-error"));
        pd.setProperty("code", "INTERNAL_ERROR");
        pd.setProperty("instance", request.getRequestURI());
        return pd;
    }
}
