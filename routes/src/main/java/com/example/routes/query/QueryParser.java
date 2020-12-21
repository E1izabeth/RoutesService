package com.example.routes.query;

import com.example.routes.query.parsing.Expr;
import com.example.routes.query.parsing.ExprParser;
import com.example.routes.query.parsing.Tokenizer;

public class QueryParser {

    private final ExprParser _exprParser;
    private final ExprToSqlExprMapper _exprMapper;

    private static Expr Symbol(String str) { return new Expr.Symbol(str); }
    private static Expr Number(Double value) { return new Expr.Number(value); }
    private static Expr Characters(String str) { return new Expr.Characters(str); }

    public QueryParser(QueryFieldsMap fieldsMap) {
        _exprParser = new ExprParser();

        final Tokenizer t = _exprParser.getTokenizer();
        new Object() {
            void unary(String name, String ch, int bp) {  t.registerUnaryOp(name, ch, bp, arg -> Symbol(name).apply(arg)); }
            void binary(String name, String ch, int bp) { t.registerBinaryOp(name, ch, bp, (a, b) -> Symbol(name).apply(a, b)); }
            {
                unary(QueryTerms.Neg, "-", 100);
                binary(QueryTerms.Sub, "-", 20);
                t.registerUnaryOp("plus", "+", 100, arg -> arg);
                binary(QueryTerms.Sum, "+", 20);
                binary(QueryTerms.Mul, "*", 30);
                binary(QueryTerms.Div, "/", 30);

                unary(QueryTerms.Not, "not", 15);
                binary(QueryTerms.Or, "or", 6);
                binary(QueryTerms.And, "and", 7);

                binary(QueryTerms.Contains, "contains", 9);

                binary(QueryTerms.Equal, "=", 9);
                binary(QueryTerms.NotEqual, "!=", 9);

                binary(QueryTerms.Less, "<", 10);
                binary(QueryTerms.Greater, ">", 10);
                binary(QueryTerms.LessOrEqual, "<=", 10);
                binary(QueryTerms.GreaterOrEqual, ">=", 10);
            }
        };

        _exprMapper = new ExprToSqlExprMapper(fieldsMap);
    }

    public SqlExpr parse(String queryText) {
        Expr expr = _exprParser.parse(queryText);
        SqlExpr sqlExpr = _exprMapper.translate(expr);
        return sqlExpr;
    }



}
