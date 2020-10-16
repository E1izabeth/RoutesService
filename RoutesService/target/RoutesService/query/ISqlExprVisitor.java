package main.webapp.query;

public interface ISqlExprVisitor<TRet> {
    TRet visitNumericFieldRef(SqlExpr.NumericFieldRef numericFieldRef);

    TRet visitStringFieldRef(SqlExpr.StringFieldRef stringFieldRef);

    TRet visitNumericMathOp(SqlExpr.NumericMathOp mathOp);

    TRet visitLogicOp(SqlExpr.LogicOp logicOp);

    TRet visitCompareNumsOp(SqlExpr.CompareNumsOp compareNumsOp);

    TRet visitLogicNotOp(SqlExpr.LogicNotOp notOp);

    TRet visitNumericNegateOp(SqlExpr.NumericNegateOp negateOp);

    TRet visitCompareStringsOp(SqlExpr.CompareStringsOp compareStringsOp);

    TRet visitNumberLiteral(SqlExpr.NumberLiteral numberLiteral);

    TRet visitStringLiteral(SqlExpr.StringLiteral stringLiteral);
}
