package com.example.routes.query;

import com.example.routes.query.parsing.QueryParsingException;

import java.util.HashMap;

public enum SqlMathOp {
    Sub,
    Sum,
    Mul,
    Div;

    private static final HashMap<SqlMathOp, String> _mathChars = new HashMap<SqlMathOp, String>() {{
        put(SqlMathOp.Sub, "-");
        put(SqlMathOp.Sum, "+");
        put(SqlMathOp.Mul, "*");
        put(SqlMathOp.Div, "/");
    }};

    public static SqlMathOp parseTerm(String term) {
        return Enum.valueOf(SqlMathOp.class, term);
    }

    public String toMathChar() {
        String result = _mathChars.get(this);

        if (result == null)
            throw new QueryParsingException("Unknown math op " + this.name());

        return result;
    }
}
