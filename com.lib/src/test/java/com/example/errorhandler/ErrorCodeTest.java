package com.example.errorhandler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {
    @Test
    void testErrorCodeProperties() {
        ErrorCode code = ErrorCode.VALIDATION_FAILED;
        assertEquals("ERR-001", code.getCode());
        assertTrue(code.getTemplate().contains("%s"));
    }
}
