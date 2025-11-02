# Error Handler Library

Centralized Java library for consistent, flexible error handling with pluggable mappings and easy servlet integration.

## Features

- **Centralized Error Codes**: Define all business and system error codes in a single `ErrorCode` enum.
- **Pluggable Mapping**: Map any exception type to an `ErrorCode` via `ErrorMapper` interface and default implementation.
- **Standard Payload**: Uniform JSON payload (`ErrorPayload`) with `code` and `message`.
- **Servlet Filter**: `ErrorHandlingFilter` catches all unhandled exceptions and serializes a proper HTTP response.

## Quick Start

### 1. Add Dependency

If using Maven, add to your `pom.xml` dependencies:

```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>error-handler-lib</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 2. Define Custom Error Codes (Optional)

Extend the `ErrorCode` enum with new codes and message templates:

```java
public enum ErrorCode {
    // existing codes...
    USER_NOT_AUTHORIZED("ERR-010", "User %s not authorized.")
    // ...
}
```

### 3. Register Mappings & Filter

In your web application initialization (e.g., `ServletContextListener`), configure the filter:

```java
DefaultErrorMapper mapper = new DefaultErrorMapper();
mapper.registerMapping(MyCustomException.class, ErrorCode.RESOURCE_NOT_FOUND);

ErrorHandlingFilter filter = new ErrorHandlingFilter(mapper);
servletContext.addFilter("errorFilter", filter)
               .addMappingForUrlPatterns(null, false, "/*");
```

### 4. Run & Test

1. Deploy your WAR/JAR to your servlet container (Tomcat, Jetty, etc.).
2. Trigger an exception in any controller or servlet.
3. Observe a JSON response with the correct HTTP status, `code`, and `message`.

## Configuration

- **HTTP Status Mapping**: Adjust `determineHttpStatus` in `ErrorHandlingFilter` to fit your API conventions.
- **Logging**: Integrate SLF4J or your logging framework of choice to capture stack traces or context.

## Examples

See `examples/webapp` for a sample `web.xml` and code-based registration in `AppConfig.java`.

## Building & Testing

```bash
mvn clean install
mvn test
```

 ## Conclusion
  This guide provides a comprehensive overview of the Error logging system, its APIs, and best practices for effective root cause analysis. By following these guidelines, you can ensure robust error handling and system monitoring.
