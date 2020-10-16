package main.webapp.query;

import main.webapp.query.parsing.QueryParsingException;

import java.util.HashMap;

public enum SqlLogicOp {
    And,
    Or,
    Equal;

    private static final HashMap<SqlLogicOp, String> _logicChars = new HashMap<SqlLogicOp, String>() {{
        put(SqlLogicOp.And, "AND");
        put(SqlLogicOp.Or, "OR");
        put(SqlLogicOp.Equal, "=");
    }};

    public static SqlLogicOp parseTerm(String term) {
        return Enum.valueOf(SqlLogicOp.class, term);
    }

    public String toLogicChar() {
        String result = _logicChars.get(this);

        if (result == null)
            throw new QueryParsingException("Unknown logic op " + this.name());

        return result;
    }
}
