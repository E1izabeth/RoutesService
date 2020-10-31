package main.webapp.query;

import main.webapp.RouteDbEntity;
import main.webapp.db.DbParametrizedQueryBuilder;
import main.webapp.db.DbParametrizedQueryInfo;

public class QueryTranslator {

    private final QueryFieldsMap _fieldsMap;
    private final QueryParser _queryParser;
    private final String _sqlViewName;

    private final long _defaultPageSize = 20;

    public long getDefaultPageSize() { return _defaultPageSize; }

    public QueryTranslator() {
        _fieldsMap = new QueryFieldsMap() {
            void put(String name, SqlType type) {
                put(name, name, type);
            }
            {
                put("name", SqlType.String);
                put("id", SqlType.Number);
                put("distance", SqlType.Number);
                put("created","creationDate", SqlType.DateTime);
                put("coordX", SqlType.Number);
                put("coordY", SqlType.Number);
                put("fromX","fromLocX", SqlType.Number);
                put("fromY","fromLocX", SqlType.Number);
                put("fromZ", "fromLocZ",SqlType.Number);
                put("toX", "toLocX", SqlType.Number);
                put("toY", "toLocY", SqlType.Number);
                put("toZ", "toLocZ", SqlType.Number);
            }
        };

        _sqlViewName = RouteDbEntity.class.getSimpleName();

        _queryParser = new QueryParser(_fieldsMap);
    }

    public void translateFilterCondition(String filterText, DbParametrizedQueryBuilder qb) {
        SqlExpr sqlExpr = _queryParser.parse(filterText);
        sqlExpr.apply(new ISqlExprVisitor<Object>() {

            private void handle(SqlExpr expr) {
                expr.apply(this);
            }

            private String addFieldRef(String fieldName) {
                //qb.format("[%1$s]",  fieldName);
                qb.append(fieldName);
                return null;
            }

            private String addBinOp(SqlExpr left, String str, SqlExpr right) {
                qb.append("(");
                this.handle(left);
                qb.append(" ").append(str).append(" ");
                this.handle(right);
                qb.append(")");
                return null;
            }

            private String addUnOp(String prefix, SqlExpr arg, String postfix) {
                qb.append(prefix).append("(");
                this.handle(arg);
                qb.append(")").append(postfix);
                return null;
            }

            public String visitNumericFieldRef(SqlExpr.NumericFieldRef numericFieldRef) { return this.addFieldRef(numericFieldRef.fieldName); }
            public String visitStringFieldRef(SqlExpr.StringFieldRef stringFieldRef) { return this.addFieldRef(stringFieldRef.fieldName); }
            public String visitDateTimeFieldRef(SqlExpr.DateTimeFieldRef dateTimeFieldRef) { return this.addFieldRef(dateTimeFieldRef.fieldName); }

            public String visitDateTimeToNumberConvOp(SqlExpr.DateTimeToNumberConvOp convOp) {
                qb.append("cast(extract(epoch from (");
                this.handle(convOp.arg);
                qb.append(")) as bigint)");
                return null;
            }

            public String visitNumberToIntegerConvOp(SqlExpr.NumberToIntegerConvOp convOp) {
                qb.append("cast((");
                this.handle(convOp.arg);
                qb.append(") as int)");
                return null;
            }

            public String visitNumericMathOp(SqlExpr.NumericMathOp mathOp) { return this.addBinOp(mathOp.left, mathOp.kind.toMathChar(), mathOp.right); }
            public String visitLogicOp(SqlExpr.LogicOp logicOp) { return this.addBinOp(logicOp.left, logicOp.kind.toLogicChar(), logicOp.right); }
            public String visitCompareNumsOp(SqlExpr.CompareNumsOp compareNumsOp) { return this.addBinOp(compareNumsOp.left, compareNumsOp.kind.toCompareChar(), compareNumsOp.right); }
            public String visitLogicNotOp(SqlExpr.LogicNotOp notOp) { return this.addUnOp("(NOT (", notOp.arg, "))"); }
            public String visitNumericNegateOp(SqlExpr.NumericNegateOp negateOp) { return this.addUnOp("(-(", negateOp.arg, "))"); }

            public String visitNumberLiteral(SqlExpr.NumberLiteral numberLiteral) { qb.appendParameter(numberLiteral.value); return null; }
            public String visitStringLiteral(SqlExpr.StringLiteral stringLiteral) { qb.appendParameter(stringLiteral.value); return null; }

            public String visitDateTimeFuncCallOp(SqlExpr.DateTimeFuncCallOp funcCallOp) {
                qb.append(funcCallOp.name).append("(");

                if (funcCallOp.args.size() > 0) {
                    this.handle(funcCallOp.args.get(0));
                    for (int i = 1; i < funcCallOp.args.size(); i++) {
                        qb.append(", ");
                        this.handle(funcCallOp.args.get(i));
                    }
                }

                qb.append(")");
                return null;
            }

            public String visitCompareStringsOp(SqlExpr.CompareStringsOp compareStringsOp) {
                if (compareStringsOp.partial) {
                    qb.append("(");
                    this.handle(compareStringsOp.left);
                    qb.append(" LIKE CONCAT('%%', REPLACE(");
                    this.handle(compareStringsOp.right);
                    qb.append(", '%%', '%%%%'), '%%'))");
                } else {
                    qb.append("(");
                    this.handle(compareStringsOp.left);
                    qb.append(" = ");
                    this.handle(compareStringsOp.right);
                    qb.append(")");
                }
                return null;
            }
        });
    }

    public String translateOrderSpec(String orderSpecText) {
        String[] parts = orderSpecText.split(",");

        StringBuilder sb = new StringBuilder();
        boolean isNonFirst = false;
        for (String fieldSpec: parts) {
            if (fieldSpec.trim().length() == 0)
                continue;;

            boolean asc = true;
            if (fieldSpec.trim().startsWith("~")) {
                fieldSpec = fieldSpec.substring(1);
                asc = false;
            }

            QueryFieldInfo fieldInfo = _fieldsMap.get(fieldSpec.trim());

            if (isNonFirst)
                sb.append(", ");

            sb.append(fieldInfo.sqlViewName).append(" ").append(asc ? "ASC" : "DESC");

            isNonFirst = true;
        }

        return sb.toString();
    }

    public DbParametrizedQueryInfo translateQuery(String filter, String orderSpec, Long from, Long to) {
        DbParametrizedQueryBuilder qb = new DbParametrizedQueryBuilder();

        boolean paginate = from != null || to != null;
        boolean filtering = filter != null && filter.trim().length() > 0;
        boolean ordering = orderSpec != null && orderSpec.trim().length() > 0;

        from = (paginate && from == null) ? Long.valueOf(0) : from;
        to = (paginate && to == null) ? Long.valueOf(from + _defaultPageSize) : to;

        String orderingSql = ordering ? this.translateOrderSpec(orderSpec) : "Id ASC";

        qb.formatLine("SELECT * FROM (");

        qb.formatLine("    SELECT ROW_NUMBER() OVER ( ORDER BY %1$s ) AS RowNum, * FROM %2$s", orderingSql, _sqlViewName);

        if (filtering) {
            qb.append("    WHERE ");
            this.translateFilterCondition(filter, qb);
            qb.appendLine();
        }

        qb.appendLine(") as Numbered");
        if (paginate) {
            qb.formatLine("WHERE RowNum > %1$s AND RowNum <= %2$s", from, to);
        }
        qb.formatLine("ORDER BY RowNum");

        return qb.buildQuery();
    }
}
