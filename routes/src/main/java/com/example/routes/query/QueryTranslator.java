package com.example.routes.query;

import com.example.routes.db.RouteDbEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QueryTranslator<T> {

    private final QueryFieldsMap _fieldsMap;
    private final QueryParser _queryParser;
    private final String _sqlViewName;

    private final int _defaultPageSize = 20;

    public int getDefaultPageSize() { return _defaultPageSize; }

    public QueryTranslator() {
        _fieldsMap = new QueryFieldsMap() {
            void put(String name, SqlType type) {
                put(name, name, type);
            }
            {
                put("name", SqlType.String);
                put("id", SqlType.Number);
                put("distance", SqlType.Number);
                put("created","creation_date", SqlType.DateTime);
                put("coordX", "coordx", SqlType.Number);
                put("coordY", "coordy", SqlType.Number);
                put("fromId","from_location_id", SqlType.Number);
                put("toId","to_location_id", SqlType.Number);
                put("fromX","from_x", SqlType.Number);
                put("fromY","from_y", SqlType.Number);
                put("fromZ", "from_z",SqlType.Number);
                put("toX", "to_x", SqlType.Number);
                put("toY", "to_y", SqlType.Number);
                put("toZ", "to_z", SqlType.Number);

                put("x", SqlType.Number);
                put("y", SqlType.Number);
                put("z", SqlType.Number);
            }
        };

        _sqlViewName = RouteDbEntity.class.getSimpleName();

        _queryParser = new QueryParser(_fieldsMap);
    }

    interface BinaryExpressionBuilder {
        Expression build(Expression left, Expression right);
    }

    interface UnaryExpressionBuilder {
        Expression build(Expression arg);
    }

    public Expression<?> translateFilterCondition(String filterText, Root<T> entity, CriteriaQuery<?> query, CriteriaBuilder builder) {
        SqlExpr sqlExpr = _queryParser.parse(filterText);
        return sqlExpr.apply(new ISqlExprVisitor<Expression>() {

            private Expression addBinOp(SqlExpr left, BinaryExpressionBuilder builder, SqlExpr right) {
                return builder.build(left.apply(this), right.apply(this));
            }

            private Expression addUnOp(UnaryExpressionBuilder builder, SqlExpr arg) {
                return builder.build(arg.apply(this));
            }

            public Expression visitNumericFieldRef(SqlExpr.NumericFieldRef numericFieldRef) { return entity.get(numericFieldRef.fieldName); }
            public Expression visitStringFieldRef(SqlExpr.StringFieldRef stringFieldRef) { return entity.get(stringFieldRef.fieldName); }
            public Expression visitDateTimeFieldRef(SqlExpr.DateTimeFieldRef dateTimeFieldRef) { return entity.get(dateTimeFieldRef.fieldName); }

            public Expression visitNumberToIntegerConvOp(SqlExpr.NumberToIntegerConvOp convOp) {
//                qb.append("cast((");
//                this.handle(convOp.arg);
//                qb.append(") as int)");
                return convOp.arg.apply(this);
            }

            public Expression visitNumericMathOp(SqlExpr.NumericMathOp mathOp) {
                BinaryExpressionBuilder op;
                switch (mathOp.kind) {
                    case Sum: op = builder::sum; break;
                    case Div: op = builder::quot; break;
                    case Mul: op = builder::prod; break;
                    case Sub: op = builder::diff; break;
                    default: throw new RuntimeException("Uexpected opKind " + mathOp.kind);
                };
                return this.addBinOp(mathOp.left, op, mathOp.right);
            }
            public Expression visitLogicOp(SqlExpr.LogicOp logicOp) {
                BinaryExpressionBuilder op;
                switch (logicOp.kind) {
                    case And: op = builder::and; break;
                    case Or: op = builder::or; break;
                    case Equal: op = builder::equal; break;
                    default: throw new RuntimeException("Uexpected opKind " + logicOp.kind);
                };
                return this.addBinOp(logicOp.left, op, logicOp.right);
            }
            private Expression makeCompareOp(SqlExpr left, SqlCompareOp opKind, SqlExpr right) {
                BinaryExpressionBuilder op;
                switch (opKind) {
                    case Equal: op = builder::equal; break;
                    case NotEqual: op = builder::notEqual; break;
                    case Greater: op = builder::greaterThan; break;
                    case GreaterOrEqual: op = builder::greaterThanOrEqualTo; break;
                    case Less: op = builder::lessThan; break;
                    case LessOrEqual: op = builder::lessThanOrEqualTo; break;
                    default: throw new RuntimeException("Uexpected opKind " + opKind);
                };
                return this.addBinOp(left, op, right);
            }
            public Expression visitCompareNumsOp(SqlExpr.CompareNumsOp compareNumsOp) {
                return this.makeCompareOp(compareNumsOp.left, compareNumsOp.kind, compareNumsOp.right);
            }
            public Expression visitCompareDatesOp(SqlExpr.CompareDatesOp compareDatesOp) {
                return this.makeCompareOp(compareDatesOp.left, compareDatesOp.kind, compareDatesOp.right);
            }

            public Expression visitLogicNotOp(SqlExpr.LogicNotOp notOp) { return builder.not(notOp.arg.apply(this)); }
            public Expression visitNumericNegateOp(SqlExpr.NumericNegateOp negateOp) { return builder.neg(negateOp.arg.apply(this)); }

            public Expression visitNumberLiteral(SqlExpr.NumberLiteral numberLiteral) { return builder.literal(numberLiteral.value); }
            public Expression visitStringLiteral(SqlExpr.StringLiteral stringLiteral) { return builder.literal(stringLiteral.value); }

            public Expression visitDateTimeFuncCallOp(SqlExpr.DateTimeFuncCallOp funcCallOp) {
                Expression[] args = new Expression[funcCallOp.args.size()];
                for (int i = 0; i < funcCallOp.args.size(); i++) {
                    args[i] = funcCallOp.args.get(i).apply(this);
                }
                return builder.function(funcCallOp.name, LocalDateTime.class, args);
            }

            public Expression visitCompareStringsOp(SqlExpr.CompareStringsOp compareStringsOp) {
                Expression<String> left = compareStringsOp.left.apply(this);
                Expression<String> right = compareStringsOp.right.apply(this);
                if (compareStringsOp.partial) {
                    return builder.like(left, builder.function("concat", String.class,builder.literal("%%"), right, builder.literal("%%")));
                } else {
                    return builder.equal(left, right);
                }
            }
        });
    }

    public Sort translateOrderSpec(String orderSpecText) {
        boolean ordering = orderSpecText != null && orderSpecText.trim().length() > 0;

        if (ordering) {
            List<Sort.Order> orderList = new ArrayList<Sort.Order>();
            String[] parts = orderSpecText.split(",");

            for (String fieldSpec : parts) {
                if (fieldSpec.trim().length() == 0)
                    continue;

                boolean asc = true;
                if (fieldSpec.trim().startsWith("~")) {
                    fieldSpec = fieldSpec.substring(1);
                    asc = false;
                }

                QueryFieldInfo fieldInfo = _fieldsMap.get(fieldSpec.trim());
                if (asc) {
                    orderList.add(Sort.Order.asc(fieldInfo.sqlViewName));
                } else {
                    orderList.add(Sort.Order.desc(fieldInfo.sqlViewName));
                }
            }

            return Sort.by(orderList);
        } else {
            return Sort.unsorted();
        }
    }

    public Specification<T> translateQuery(String filter) {
        boolean filtering = filter != null && filter.trim().length() > 0;

        if (filtering) {
            return (root, query, builder) -> {
                return (Predicate) this.translateFilterCondition(filter, root, query, builder);
            };
        } else {
            return null;
        }
    }
}
