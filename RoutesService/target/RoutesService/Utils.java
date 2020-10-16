package main.webapp;

import main.webapp.query.parsing.QueryParsingException;

public class Utils {
    public static <T> T is(Class<T> type, Object obj, T def) {
        return type.isInstance(obj) ? (T) obj : def;
    }

    public static <T> T as(Class<T> type, Object obj) {
        return as(type, obj, "Unexpected " + (obj == null ? "NULL" : obj.getClass().getName()) + " while expecting " + type.getName());
    }

    public static <T> T as(Class<T> type, Object obj, String errorMessage) {
        if (!type.isInstance(obj))
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

    public static String readAsStringTillEnd(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
