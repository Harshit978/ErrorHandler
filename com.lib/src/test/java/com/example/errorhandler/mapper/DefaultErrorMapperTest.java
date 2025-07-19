package com.example.errorhandler.mapper;

import com.example.errorhandler.DefaultErrorMapper;
import com.example.errorhandler.ErrorCode;
import com.example.errorhandler.ErrorPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultErrorMapperTest {

    private DefaultErrorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DefaultErrorMapper();
        mapper.registerMapping(IllegalStateException.class, ErrorCode.PERMISSION_DENIED);
    }

    @Test
    void testKnownExceptionMapping() {
        Throwable t = new IllegalArgumentException("fieldName");
        ErrorPayload payload = mapper.toError(t);
        assertEquals(ErrorCode.VALIDATION_FAILED.getCode(), payload.code());
        assertTrue(payload.message().contains("fieldName"));
    }

    @Test
    void testCustomExceptionMapping() {
        Throwable t = new IllegalStateException("state");
        ErrorPayload payload = mapper.toError(t);
        assertEquals(ErrorCode.PERMISSION_DENIED.getCode(), payload.code());
    }

    @Test
    void testUnknownExceptionDefaults() {
        Throwable t = new RuntimeException("oops");
        ErrorPayload payload = mapper.toError(t);
        assertEquals(ErrorCode.UNKNOWN_ERROR.getCode(), payload.code());
    }
}
