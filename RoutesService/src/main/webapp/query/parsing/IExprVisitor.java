package main.webapp.query.parsing;

public interface IExprVisitor<T>
{
    T visitApply(Expr.Apply apply);
    T visitNumber(Expr.Number number);
    T visitCharacters(Expr.Characters str);
    T visitSymbol(Expr.Symbol symbol);
}

