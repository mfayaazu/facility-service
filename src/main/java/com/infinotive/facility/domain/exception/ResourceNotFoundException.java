package com.infinotive.facility.domain.exception;

/**
 * Thrown when a requested resource cannot be found for the current tenant.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
