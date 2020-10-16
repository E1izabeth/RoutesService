package main.webapp.db;

import main.webapp.query.SqlCompareOp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQueryResult implements AutoCloseable {
    private final Statement _statement;

    private final ResultSet _set;

    public DbQueryResult(Statement statement, ResultSet resultSet) {
        _statement = statement;
        _set = resultSet;
    }

    @Override
    public void close() throws SQLException {
        _set.close();
        _statement.close();
    }

    public boolean nextRow() throws SQLException {
        return _set.next();
    }

    public <T> T getRowField(int columnIndex, Class<T> desiredType) throws SQLException {
        if (boolean.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getBoolean(columnIndex);
        } else if (long.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getLong(columnIndex);
        } else if (int.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getInt(columnIndex);
        } else if (double.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getDouble(columnIndex);
        } else if (float.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getFloat(columnIndex);
        } else {
            return _set.getObject(columnIndex, desiredType);
        }
    }

    public <T> T getRowField(String fieldName, Class<T> desiredType) throws SQLException {
        if (boolean.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getBoolean(fieldName);
        } else if (long.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getLong(fieldName);
        } else if (int.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getInt(fieldName);
        } else if (double.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getDouble(fieldName);
        } else if (float.class.isAssignableFrom(desiredType)) {
            return (T)(Object)_set.getFloat(fieldName);
        } else {
            return _set.getObject(fieldName, desiredType);
        }
    }
}
