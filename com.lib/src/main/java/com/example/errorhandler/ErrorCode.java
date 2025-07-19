package com.example.errorhandler;

public enum ErrorCode {
    UNKNOWN_ERROR("ERR-000", "An unknown error occurred."),
    VALIDATION_FAILED("ERR-001", "Validation failed for field: %s."),
    RESOURCE_NOT_FOUND("ERR-002", "Resource not found: %s."),
    PERMISSION_DENIED("ERR-003", "Permission denied for resource: %s."),
    UNPROCESSABLE_ENTITY("ERR-004", "Unprocessable entity: %s.");

    private final String code;
    private final String template;

    ErrorCode(String code, String template) {
        this.code = code;
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public String getTemplate() {
        return template;
    }
}
