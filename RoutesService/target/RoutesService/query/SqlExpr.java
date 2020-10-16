package main.webapp.query;

public abstract class SqlExpr {
    public final SqlType type;

    protected SqlExpr(SqlType type) {
        this.type = type;
    }

    public <TRet> TRet apply(ISqlExprVisitor<TRet> visitor) {
        return this.applyImpl(visitor);
    }

    protected abstract <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor);

    public abstract static class Numeric extends SqlExpr {
        public Numeric() {
            super(SqlType.Number);
        }
    }
    public abstract static class Logic extends SqlExpr {
        public Logic() {
            super(SqlType.Bool);
        }
    }
    public abstract static class Stringy extends SqlExpr {
        public Stringy() {
            super(SqlType.String);
        }
    }

    public static class NumberLiteral extends Numeric {
        public final double value;

        public NumberLiteral(double value) {
            this.value = value;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitNumberLiteral(this);
        }
    }
    public static class StringLiteral extends Stringy {
        public final String value;

        public StringLiteral(String value) {
            this.value = value;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitStringLiteral(this);
        }
    }

    public static class NumericFieldRef extends Numeric {
        public final String fieldName;

        public NumericFieldRef(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitNumericFieldRef(this);
        }
    }
    public static class StringFieldRef extends Stringy {
        public final String fieldName;

        public StringFieldRef(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitStringFieldRef(this);
        }
    }

    public static class NumericMathOp extends Numeric {
        public final SqlMathOp kind;
        public final Numeric left, right;

        public NumericMathOp(SqlMathOp kind, Numeric left, Numeric right) {
            this.kind = kind;
            this.left = left;
            this.right = right;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitNumericMathOp(this);
        }
    }
    public static class LogicOp extends Logic {
        public final SqlLogicOp kind;
        public final Logic left, right;

        public LogicOp(SqlLogicOp kind, Logic left, Logic right) {
            this.kind = kind;
            this.left = left;
            this.right = right;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitLogicOp(this);
        }
    }
    public static class CompareNumsOp extends Logic {
        public final SqlCompareOp kind;
        public final Numeric left, right;

        public CompareNumsOp(SqlCompareOp kind, Numeric left, Numeric right) {
            this.kind = kind;
            this.left = left;
            this.right = right;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitCompareNumsOp(this);
        }
    }
    public static class LogicNotOp extends Logic {
        public final Logic arg;

        public LogicNotOp(Logic arg) {
            this.arg = arg;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitLogicNotOp(this);
        }
    }
    public static class NumericNegateOp extends Numeric {
        public final Numeric arg;

        public NumericNegateOp(Numeric arg) {
            this.arg = arg;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitNumericNegateOp(this);
        }
    }
    public static class CompareStringsOp extends Logic {
        public final Stringy left, right;
        public final boolean partial;

        public CompareStringsOp(boolean partial, Stringy left, Stringy right) {
            this.partial = partial;
            this.left = left;
            this.right = right;
        }

        @Override
        protected <TRet> TRet applyImpl(ISqlExprVisitor<TRet> visitor) {
            return visitor.visitCompareStringsOp(this);
        }
    }
}
