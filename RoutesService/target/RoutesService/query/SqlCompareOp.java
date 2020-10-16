package main.webapp.query;

import main.webapp.query.parsing.QueryParsingException;

import java.util.HashMap;

public enum SqlCompareOp {
    Equal,
    NotEqual,
    Greater,
    GreaterOrEqual,
    Less,
    LessOrEqual;

    private static final HashMap<SqlCompareOp, String> _mathChars = new HashMap<SqlCompareOp, String>() {{
        put(SqlCompareOp.Equal, "=");
        put(SqlCompareOp.NotEqual, "<>");
        put(SqlCompareOp.Greater, ">");
        put(SqlCompareOp.GreaterOrEqual, ">=");
        put(SqlCompareOp.Less, "<");
        put(SqlCompareOp.LessOrEqual, "<=");
    }};

    public static SqlCompareOp parseTerm(String term) {
        return Enum.valueOf(SqlCompareOp.class, term);
    }

    public String toCompareChar() {
        String result = _mathChars.get(this);

        if (result == null)
            throw new QueryParsingException("Unknown compare op " + this.name());

        return result;
    }
}
