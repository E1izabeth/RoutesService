package com.example.routes.query;

public interface ISqlExprVisitor<TRet> {
    TRet visitNumericFieldRef(SqlExpr.NumericFieldRef numericFieldRef);

    TRet visitStringFieldRef(SqlExpr.StringFieldRef stringFieldRef);

    TRet visitDateTimeFieldRef(SqlExpr.DateTimeFieldRef timezoneFieldRef);

    TRet visitNumericMathOp(SqlExpr.NumericMathOp mathOp);

    TRet visitLogicOp(SqlExpr.LogicOp logicOp);

    TRet visitCompareNumsOp(SqlExpr.CompareNumsOp compareNumsOp);

    TRet visitLogicNotOp(SqlExpr.LogicNotOp notOp);

    TRet visitNumericNegateOp(SqlExpr.NumericNegateOp negateOp);

    TRet visitCompareStringsOp(SqlExpr.CompareStringsOp compareStringsOp);

    TRet visitNumberLiteral(SqlExpr.NumberLiteral numberLiteral);

    TRet visitStringLiteral(SqlExpr.StringLiteral stringLiteral);

    TRet visitDateTimeFuncCallOp(SqlExpr.DateTimeFuncCallOp funcCallOp);

    // TRet visitDateTimeToNumberConvOp(SqlExpr.DateTimeToNumberConvOp convOp);

    TRet visitNumberToIntegerConvOp(SqlExpr.NumberToIntegerConvOp convOp);

    TRet visitCompareDatesOp(SqlExpr.CompareDatesOp compareDatesOp);
}
