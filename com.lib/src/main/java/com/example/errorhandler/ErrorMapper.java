package com.example.errorhandler;

public interface ErrorMapper {
    ErrorPayload toError(Throwable t);
}
