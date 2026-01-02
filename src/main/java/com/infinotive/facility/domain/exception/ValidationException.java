package com.infinotive.facility.domain.exception;

/**
 * Thrown when input data is syntactically valid JSON,
 * but semantically invalid for the operation.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
