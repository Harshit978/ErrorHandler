package com.example.errorhandler.filter;

import com.example.errorhandler.DefaultErrorMapper;
import com.example.errorhandler.ErrorHandlingFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.servlet.ServletException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ErrorHandlingFilterTest {

    private ErrorHandlingFilter filter;
    private ServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private StringWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        DefaultErrorMapper mapper = new DefaultErrorMapper();
        filter = new ErrorHandlingFilter(mapper);
        request = mock(ServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);

        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void testDoFilterHandlesException() throws IOException, ServletException {
        doThrow(new IllegalArgumentException("input")).when(chain).doFilter(request, response);

        filter.doFilter(request, response, chain);

        verify(response).setStatus(400);
        String json = writer.toString();
        assertTrue(json.contains("ERR-001"));
        assertTrue(json.contains("input"));
    }
}
