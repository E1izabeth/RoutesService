package com.example.routes;

import com.example.routes.query.parsing.QueryParsingException;

import javax.servlet.http.HttpServletResponse;

public class Utils {
    public static <T> T is(Class<T> type, Object obj, T def) {
        return type.isInstance(obj) ? (T) obj : def;
    }

    public static <T> T as(Class<T> type, Object obj) {
        return as(type, obj, "Unexpected " + (obj == null ? "NULL" : obj.getClass().getName()) + " while expecting " + type.getName());
    }

    public static <T> T as(Class<T> type, Object obj, String errorMessage) {
        if (!type.isAssignableFrom(obj.getClass()))
            throw new QueryParsingException(errorMessage);

        return (T)obj;
    }

    public static Long parseMaybeLong(String str) {
        try {
            return str == null ? null : Long.parseLong(str);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Long parseLongOrNothing(String str) {
        if (str == null || str.trim().length() == 0)
            return null;

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException ex) {
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Number expected", ex);
        }
    }

    public static String readAsStringTillEnd(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
