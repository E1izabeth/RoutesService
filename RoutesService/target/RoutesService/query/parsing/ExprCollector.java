package main.webapp.query.parsing;

import java.util.stream.Collectors;

public class ExprCollector implements IExprVisitor<String> {
    public static final ExprCollector instance = new ExprCollector();


    public String visitApply(Expr.Apply apply) {
        return apply.head.apply(this) + "["
                + String.join(", ", apply.args.stream().map(a -> a.apply(this)).collect(Collectors.toList()))
                + "]";
    }

    public String visitNumber(Expr.Number number) {
        return Double.toString(number.value);
    }

    public String visitCharacters(Expr.Characters str) {
        return "\"" + str.text + "\"";
    }

    public String visitSymbol(Expr.Symbol symbol) {
        return symbol.name;
    }
}