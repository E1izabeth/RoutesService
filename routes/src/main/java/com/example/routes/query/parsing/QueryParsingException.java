package com.example.routes.query.parsing;

import com.example.routes.RestApiException;

import javax.servlet.http.HttpServletResponse;

public class QueryParsingException extends RestApiException {
    public QueryParsingException(String message) {
        super(HttpServletResponse.SC_BAD_REQUEST, message);
    }
}
