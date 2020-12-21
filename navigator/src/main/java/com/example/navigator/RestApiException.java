package com.example.navigator;

public class RestApiException extends RuntimeException {
    public final int statusCode;
    public final String stackTracePrefix;

    public RestApiException(int statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
        this.stackTracePrefix = null;
    }

    public RestApiException(int statusCode, String message, String stackTracePrefix) {
        super(message);

        this.statusCode = statusCode;
        this.stackTracePrefix = stackTracePrefix;
    }

    public RestApiException(int statusCode, String message, Throwable inner) {
        super(message, inner);

        this.statusCode = statusCode;
        this.stackTracePrefix = null;
    }
}
