package com.example.errorhandler;


import java.util.HashMap;
import java.util.Map;

public class DefaultErrorMapper implements ErrorMapper {

    private final Map<Class<? extends Throwable>, ErrorCode> mapping = new HashMap<>();

    public DefaultErrorMapper() {
        // default mappings
        mapping.put(IllegalArgumentException.class, ErrorCode.VALIDATION_FAILED);
        mapping.put(NullPointerException.class, ErrorCode.UNKNOWN_ERROR);
        // add more as needed
    }

    public void registerMapping(Class<? extends Throwable> exceptionClass, ErrorCode code) {
        mapping.put(exceptionClass, code);
    }

    @Override
    public ErrorPayload toError(Throwable t) {
        ErrorCode code = mapping.getOrDefault(t.getClass(), ErrorCode.UNKNOWN_ERROR);
        String msg;
        if (t.getMessage() != null && code.getTemplate().contains("%s")) {
            msg = String.format(code.getTemplate(), t.getMessage());
        } else {
            msg = code.getTemplate();
        }
        return new ErrorPayload(code.getCode(), msg);
    }
}
