package main.webapp.db;

import main.webapp.query.QueryTranslator;
import main.webapp.query.SqlExpr;
import main.webapp.query.StringBuilderEx;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbContext implements AutoCloseable {

    public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    private static final DbTypesMapping _typesMap;

    static {
        _typesMap = new DbTypesMapping() {{
            put(String.class, "text");
            put(boolean.class, "boolean");
            put(int.class, "integer");
            put(long.class, "bigint");
            put(float.class, "real");
            put(double.class, "double precision");
            put(LocalDateTime.class, "TIMESTAMP WITH TIME ZONE");
        }};
    }

    private final HashMap<Class, DbEntityInfo> _knownEntities = new HashMap<>();

    private final DbConnection _cnn;

    public DbContext(DbConnection cnn){
        _cnn = cnn;
    }

    private DbEntityInfo getEntityInfo(Class type) {
        DbEntityInfo info = _knownEntities.get(type);
        if (info == null) {
            _knownEntities.put(type, info = this.collectEntityInfo(type));
        }
        return info;
    }

    private DbEntityInfo collectEntityInfo(Class type) {
        ArrayList<DbEntityFieldInfo> fields = new ArrayList<>();
        for (Field field : type.getFields()){
            String dbType = _typesMap.get(field.getType());
            boolean isDbGenerated = false;
            boolean isReadOnly = false;

            DbField fieldAnnotation = field.getAnnotation(DbField.class);
            if (fieldAnnotation != null) {
                if (fieldAnnotation.dbType() != null && fieldAnnotation.dbType().length() > 0)
                    dbType = fieldAnnotation.dbType();

                isDbGenerated = fieldAnnotation.isDbGenerated();
                isReadOnly = fieldAnnotation.isReadOnly();
            }

            fields.add(new DbEntityFieldInfo(field.getName(), dbType, field, isDbGenerated, isReadOnly));
        }
        return new DbEntityInfo(type.getSimpleName(), type, fields);
    }

    public void createTable(Class type) throws SQLException {
        _cnn.createTable(this.getEntityInfo(type));
    }

    private <T> Iterable<T> selectImpl(DbEntityInfo info, DbParametrizedQueryInfo query) throws SQLException {
        return new DbEntityQueryResult<T>(_cnn, info, query);
    }

    public <T> Iterable<T> select(Class<T> type) throws SQLException {
        DbEntityInfo entityInfo = this.getEntityInfo(type);
        DbParametrizedQueryBuilder qb = new DbParametrizedQueryBuilder();
        qb.formatLine("SELECT * FROM [%1$s]", entityInfo.name);
        return this.selectImpl(entityInfo, qb.buildQuery());
    }

    public <T> Iterable<T> select(Class<T> type, DbParametrizedQueryInfo query) throws SQLException {
        return this.selectImpl(this.getEntityInfo(type), query);
    }

    public void mutate(DbParametrizedQueryInfo query) throws SQLException {
        _cnn.executeMutation(query.sql, query.parameters);
    }

    public <T> void update(T obj, DbParametrizedQueryInfo sqlCondition) throws SQLException, IllegalAccessException {
        DbEntityInfo info = this.getEntityInfo(obj.getClass());

        DbParametrizedQueryBuilder qb = new DbParametrizedQueryBuilder();
        qb.append("UPDATE ").append(info.name).append(" SET ");

        boolean isNonFirst = false;
        for (DbEntityFieldInfo field : info.entityFields) {
            if (!field.isReadOnly) {
                if (isNonFirst)
                    qb.append(", ");

                qb.append(field.name);
                qb.append(" = ");
                qb.appendParameter(field.runtimeField.get(obj));
                isNonFirst = true;
            }
        }

        qb.append(" WHERE ");
        qb.append(sqlCondition.sql);
        sqlCondition.parameters.forEach(p -> qb._parameters.add(new DbQueryParameter(qb._parameters.size() + 1, p.value)));

        this.mutate(qb.buildQuery());
    }

    public <T> void insert(T obj) throws SQLException, IllegalAccessException {
        DbEntityInfo info = this.getEntityInfo(obj.getClass());

        DbParametrizedQueryBuilder qb = new DbParametrizedQueryBuilder();
        qb.append("INSERT INTO ").append(info.name).append("(")
          .append(String.join(", ", info.entityFields.stream().filter(f -> !f.isDbGenerated).map(f -> f.name).collect(Collectors.toList())))
          .appendLine(")");

        qb.append("VALUES (");

        boolean isNonFirst = false;
        for (DbEntityFieldInfo field : info.entityFields) {
            if (!field.isDbGenerated) {
                if (isNonFirst)
                    qb.append(", ");

                qb.appendParameter(field.runtimeField.get(obj));
                isNonFirst = true;
            }
        }

        qb.appendLine(")");

        List<String> results = info.entityFields.stream().filter(f -> f.isDbGenerated).map(f -> f.name).collect(Collectors.toList());
        if (results.size() > 0) {
            qb.append("RETURNING ").appendLine(String.join(", ", results));
            DbParametrizedQueryInfo query = qb.buildQuery();
            try (DbQueryResult result = _cnn.executeQuery(query.sql, query.parameters)) {
                result.nextRow();
                for (DbEntityFieldInfo field : info.entityFields) {
                    if (field.isDbGenerated) {
                        field.runtimeField.set(obj, result.getRowField(field.name, field.runtimeField.getType()));
                    }
                }
            }
        } else {
            DbParametrizedQueryInfo query = qb.buildQuery();
            _cnn.executeMutation(query.sql, query.parameters);
        }
    }

    public <T> T compute(Class<T> type, DbParametrizedQueryInfo query) throws SQLException {
        return _cnn.compute(type, query.sql, query.parameters);
    }

    public boolean isLocalTableExists(Class type) throws SQLException {
        DbEntityInfo info = this.getEntityInfo(type);

        Iterable<DbSchemaTableEntity> tabs = this.select(
                DbSchemaTableEntity.class,
                new DbParametrizedQueryInfo(
                        "SELECT * FROM pg_catalog.pg_tables WHERE tableowner = ? AND tablename ilike ?", _cnn.userName, info.name
                )
        );
        List<DbSchemaTableEntity> result = StreamSupport.stream(tabs.spliterator(), false).collect(Collectors.toList());
        return result.size() > 0;
    }

    @Override
    public void close() throws Exception {
        _cnn.close();
    }
}
