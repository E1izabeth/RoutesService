package main.webapp.query;

import main.webapp.Utils;
import main.webapp.query.parsing.Expr;
import main.webapp.query.parsing.IExprVisitor;
import main.webapp.query.parsing.QueryParsingException;

import java.time.*;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExprToSqlExprMapper implements IExprVisitor<SqlExpr> {

    private final QueryFieldsMap _queryFields;

    public ExprToSqlExprMapper(QueryFieldsMap queryFields) {
        _queryFields = queryFields;
    }

    public SqlExpr translate(Expr expr) {
        return expr.apply(this);
    }

    private <TRet extends SqlExpr> TRet translateTo(Class<TRet> type, Expr expr) {
        SqlExpr sqlExpr = expr.apply(this);
        return Utils.as(type, sqlExpr);
    }

    @Override
    public SqlExpr visitApply(Expr.Apply apply) {

        Expr.Symbol head = Utils.as(Expr.Symbol.class, apply.head, "Unexpected complex expression head");

        SqlExpr result;
        switch (head.name) {
            case QueryTerms.Neg:
            case QueryTerms.Not: {
                result = this.translateUnary(head.name, apply.args.get(0));
            }
            break;
            case QueryTerms.Sum:
            case QueryTerms.Sub:
            case QueryTerms.Mul:
            case QueryTerms.Div:
            case QueryTerms.And:
            case QueryTerms.Or:
            case QueryTerms.Contains:
            case QueryTerms.Equal:
            case QueryTerms.NotEqual:
            case QueryTerms.Less:
            case QueryTerms.Greater:
            case QueryTerms.LessOrEqual:
            case QueryTerms.GreaterOrEqual: {
                result = this.translateBinary(head.name, apply.args.get(0), apply.args.get(1));
            }
            break;
            default: {
                if (head.name.toLowerCase().equals("time")) {
                    result = this.translateTimeLiteral(apply);
                } else {
                    throw new QueryParsingException("Unexpected complex expression kind " + head.name);
                }
            }
        }

        return result;
    }

    private SqlExpr translateTimeLiteral(Expr.Apply apply) {
        if (apply.args.size() > 7 || apply.args.size() < 1) {
            throw new QueryParsingException("DateTime spec should be time(year, month, day, hour, minute, second, timeZoneOffsetSeconds)");
        }
        // make_timestamptz(year int, month int, day int, hour int, min int, sec double precision, [ timezone text ])
        // make_timestamptz(2013,     7,         15,      8,        15,      23.5)

        List<SqlExpr> parts = IntStream.range(0, Math.min(6, apply.args.size()))
                .mapToObj(i -> new SqlExpr.NumberToIntegerConvOp(this.translateTo(SqlExpr.Numeric.class, apply.args.get(i))))
                .collect(Collectors.toList());

        while (parts.size() < 6) {
            parts.add(new SqlExpr.NumberToIntegerConvOp(new SqlExpr.NumberLiteral(0)));
        }

        if (apply.args.size() > 6) {
            SqlExpr.StringLiteral tzspec = this.translateTo(SqlExpr.StringLiteral.class, apply.args.get(6));
            parts.add(new SqlExpr.StringLiteral(tzspec.value));
            this.validateTime(apply.args, tzspec.value);
        } else {
            parts.add(new SqlExpr.StringLiteral("UTC"));
            this.validateTime(apply.args, "UTC");
        }

        return new SqlExpr.DateTimeToNumberConvOp(
                new SqlExpr.DateTimeFuncCallOp("make_timestamptz", parts)
        );
    }

    private void validateTime(List<Expr> parts, String zone) {
        try {
            int year = this.getDateNumber(parts, 0);
            int month = this.getDateNumber(parts, 1);
            int day = this.getDateNumber(parts, 2);
            int hours = this.getDateNumber(parts, 3);
            int minutes = this.getDateNumber(parts, 4);
            int seconds = this.getDateNumber(parts, 5);
            ZonedDateTime stamp = ZonedDateTime.of(year, month, day, hours, minutes, seconds, 0, ZoneId.of(zone));
        } catch (DateTimeException ex) {
            throw new QueryParsingException("Invalid time spec");
        }
    }

    private int getDateNumber(List<Expr> parts, int index) {
        Expr part = parts.size() > index ? parts.get(index) : null;

        if (part == null) {
            return -1;
        } else if (part instanceof Expr.Number) {
            Expr.Number expr = (Expr.Number) part;
            return (int) expr.value;
        } else {
            return 0;
        }
    }

    private SqlExpr translateUnary(String term, Expr arg) {
        SqlExpr result;
        switch (term) {
            case QueryTerms.Neg: {
                result = new SqlExpr.NumericNegateOp(translateTo(SqlExpr.Numeric.class, arg));
            }
            break;
            case QueryTerms.Not: {
                result = new SqlExpr.LogicNotOp(translateTo(SqlExpr.Logic.class, arg));
            }
            break;
            default:
                throw new QueryParsingException("Unexpected unary expression kind " + term);
        }
        return result;
    }

    private SqlExpr translateBinary(String term, Expr left, Expr right) {
        SqlExpr result;
        switch (term) {
            case QueryTerms.Sum:
            case QueryTerms.Sub:
            case QueryTerms.Mul:
            case QueryTerms.Div: {
                result = new SqlExpr.NumericMathOp(SqlMathOp.parseTerm(term), this.translateTo(SqlExpr.Numeric.class, left), this.translateTo(SqlExpr.Numeric.class, right));
            }
            break;
            case QueryTerms.And:
            case QueryTerms.Or: {
                result = new SqlExpr.LogicOp(SqlLogicOp.parseTerm(term), this.translateTo(SqlExpr.Logic.class, left), this.translateTo(SqlExpr.Logic.class, right));
            }
            break;
            case QueryTerms.Contains: {
                result = new SqlExpr.CompareStringsOp(true, this.translateTo(SqlExpr.Stringy.class, left), this.translateTo(SqlExpr.Stringy.class, right));
            }
            break;
            case QueryTerms.Equal:
            case QueryTerms.NotEqual: {
                SqlExpr sqlLeft = this.translate(left);
                SqlExpr sqlRight = this.translate(right);

                if (sqlLeft.type != sqlRight.type)
                    throw new QueryParsingException("Cannot compare inconsistent values");

                switch (sqlLeft.type) {
                    case Bool: {
                        result = new SqlExpr.LogicOp(SqlLogicOp.Equal, Utils.as(SqlExpr.Logic.class, sqlLeft), Utils.as(SqlExpr.Logic.class, sqlRight));
                    }
                    break;
                    case String: {
                        result = new SqlExpr.CompareStringsOp(false, Utils.as(SqlExpr.Stringy.class, sqlLeft), Utils.as(SqlExpr.Stringy.class, sqlRight));
                    }
                    break;
                    case Number: {
                        result = this.makeCompareNumsOp(term, Utils.as(SqlExpr.Numeric.class, sqlLeft), Utils.as(SqlExpr.Numeric.class, sqlRight));
                    }
                    break;
                    default:
                        throw new QueryParsingException("Unexpected value kind to compare for equality");
                }
            }
            break;
            case QueryTerms.Less:
            case QueryTerms.Greater:
            case QueryTerms.LessOrEqual:
            case QueryTerms.GreaterOrEqual: {
                result = this.makeCompareNumsOp(term, this.translateTo(SqlExpr.Numeric.class, left), this.translateTo(SqlExpr.Numeric.class, right));
            }
            break;
            default:
                throw new QueryParsingException("Unexpected binary expression kind " + term);
        }
        return result;
    }

    private SqlExpr makeCompareNumsOp(String term, SqlExpr.Numeric left, SqlExpr.Numeric right) {
        return new SqlExpr.CompareNumsOp(SqlCompareOp.parseTerm(term), left, right);
    }

    @Override
    public SqlExpr visitNumber(Expr.Number number) {
        return new SqlExpr.NumberLiteral(number.value);
    }

    @Override
    public SqlExpr visitCharacters(Expr.Characters str) {
        return new SqlExpr.StringLiteral(str.text);
    }

    @Override
    public SqlExpr visitSymbol(Expr.Symbol symbol) {
        QueryFieldInfo fieldInfo = _queryFields.get(symbol.name);
        SqlExpr result;

        switch (fieldInfo.type) {
            case String:
                result = new SqlExpr.StringFieldRef(fieldInfo.sqlViewName);
                break;
            case Number:
                result = new SqlExpr.NumericFieldRef(fieldInfo.sqlViewName);
                break;
            case DateTime:
                result = new SqlExpr.DateTimeToNumberConvOp(new SqlExpr.DateTimeFieldRef(fieldInfo.sqlViewName));
                break;
            case Bool:
            default:
                throw new QueryParsingException("Unexpected query field Sql type " + fieldInfo.type.name());
        }

        return result;
    }
}
