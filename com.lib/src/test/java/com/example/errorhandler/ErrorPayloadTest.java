package com.example.errorhandler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorPayloadTest {
    @Test
    void testPayloadFields() {
        ErrorPayload payload = new ErrorPayload("ERR-123", "Test message");
        assertEquals("ERR-123",payload.code());
        assertEquals("Test message", payload.message());
    }
}
