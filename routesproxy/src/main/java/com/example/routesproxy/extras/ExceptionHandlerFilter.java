package com.example.routesproxy.extras;

import com.example.routesproxy.MySerializer;
import com.example.routesproxy.RestApiException;
import com.example.routesproxy.Utils;
import xml.webapp.main.ErrorInfoType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Throwable e) {
            prepareError(response, e);
        }
    }

    private void prepareError(HttpServletResponse resp, Throwable ex) throws IOException {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(buff));
        String info = Utils.readAsStringTillEnd(new ByteArrayInputStream(buff.toByteArray()));

        Logger log = Logger.getLogger(getClass().getName());
        log.severe(info);

        ErrorInfoType errorInfo = new ErrorInfoType();
        errorInfo.setType(ex.getClass().getTypeName());
        errorInfo.setMessage(ex.getMessage());
        errorInfo.setStackTrace(info);

        Throwable cause = ex;
        while (cause != null) {
            RestApiException apiEx = Utils.is(RestApiException.class, cause, null);
            if (apiEx != null) {
                resp.setStatus(apiEx.statusCode);
                break;
            }
            cause = cause.getCause();
        }
        if (cause == null) {
            resp.setStatus(500);
        }

        resp.setContentType("text/xml;charset=UTF-8");

        try {
            PrintWriter out = resp.getWriter();
            new MySerializer().serialize(errorInfo, out);
            out.close();
        } catch (Throwable ex2) {
            log.warning("Failed to send error info");
        }
    }
}
