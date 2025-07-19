package com.example.app;

import com.example.errorhandler.ErrorCode;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    /**
     * Constructs a new CustomException with the specified ErrorCode and message arguments.
     * @param errorCode the error code enum
     * @param args arguments to format into the error message template
     */
    public CustomException(ErrorCode errorCode, Object... args) {
        super(formatMessage(errorCode, args));
        this.errorCode = errorCode;
    }

    private static String formatMessage(ErrorCode code, Object... args) {
        String template = code.getTemplate();
        if (template.contains("%s") && args != null && args.length > 0) {
            return String.format(template, args);
        }
        return template;
    }

    /**
     * Returns the associated ErrorCode for this exception.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
