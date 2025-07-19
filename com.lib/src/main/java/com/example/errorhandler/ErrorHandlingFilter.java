package com.example.errorhandler;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ErrorHandlingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingFilter.class);
    private final ErrorMapper mapper;

    public ErrorHandlingFilter(ErrorMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // Catch Exceptions only (Errors propagate to the container)
            handleException(e, response);
        }
    }

    private void handleException(Exception ex, ServletResponse response) throws IOException {

        ErrorPayload payload = mapper.toError(ex);
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(determineHttpStatus(payload.code()));
        resp.setContentType("application/json");
        String json = String.format(
                "{\"code\":\"%s\",\"message\":\"%s\"}",
                payload.code(), payload.message()
        );
        resp.getWriter().write(json);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private int determineHttpStatus(String code) {
        // simple mapping, extend as needed
        return switch (code) {
            case "ERR-001" -> 400;
            case "ERR-002" -> 404;
            case "ERR-003" -> 403;
            default -> 500;
        };
    }

}
