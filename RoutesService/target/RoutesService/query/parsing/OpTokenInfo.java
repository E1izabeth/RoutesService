package main.webapp.query.parsing;

import java.util.regex.Pattern;

public class OpTokenInfo {
    public final String name;
    public final String symbol;
    public final Pattern pattern;

    public Integer unaryBindingPower;
    public Integer binaryBindingPower;
    public boolean inverseBinaryAssociativity;
    public Func1<Expr, Expr> unaryHandler;
    public Func2<Expr, Expr, Expr> binaryHandler;

    public OpTokenInfo(String name, String symbol, Pattern pattern) {
        this.name = name;
        this.symbol = symbol;
        this.pattern = pattern;
    }
}
