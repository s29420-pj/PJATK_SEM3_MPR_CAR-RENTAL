package org.carrental.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private String field;
    private String message;
    private Map<String, String> errors;

    public ValidationException(String message, String field) {
        super(message);
        this.field = field;
        this.message = message;
    }

    public ValidationException(Map<String, String> validationErrors) {
        this.errors = validationErrors;
    }

    @Override
    public String getMessage() {
        return field + " " + message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}