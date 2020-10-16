package main.webapp.db;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Iterator;

public class DbEntityQueryResult<T> implements Iterable<T> {

    private final DbConnection _cnn;
    private final DbEntityInfo _info;
    private final DbParametrizedQueryInfo _query;

    public DbEntityQueryResult(DbConnection cnn, DbEntityInfo info, DbParametrizedQueryInfo query) throws SQLException {
        _cnn = cnn;
        _info = info;
        _query = query;
    }

    @Override
    public Iterator<T> iterator() {
        try {
            final DbQueryResult result = _cnn.executeQuery(_query.sql, _query.parameters);
            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    try {
                        boolean ok = result.nextRow();
                        if (!ok) {
                            result.close();
                        }
                        return ok;
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public T next() {
                    try {
                        Constructor ctor = _info.runtimeType.getConstructor();
                        Object obj = ctor.newInstance();

                        for (DbEntityFieldInfo field : _info.entityFields) {
                            Object value = result.getRowField(field.name, field.runtimeField.getType());
                            field.runtimeField.set(obj, value);
                        }
                        return  (T)obj;
                    } catch (Throwable ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
