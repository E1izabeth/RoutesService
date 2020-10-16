package main.webapp.query.parsing;

import main.webapp.RestApiException;

import javax.servlet.http.HttpServletResponse;

public class QueryParsingException extends RestApiException {
    public QueryParsingException(String message) {
        super(HttpServletResponse.SC_BAD_REQUEST, message);
    }
}
