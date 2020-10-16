package main.webapp;

import javax.servlet.http.HttpServletResponse;

public class RestApiException extends RuntimeException {
    public final int statusCode;

    public RestApiException(int statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    public RestApiException(int statusCode, String message, Throwable inner) {
        super(message, inner);

        this.statusCode = statusCode;
    }
}
