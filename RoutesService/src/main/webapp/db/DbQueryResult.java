package main.webapp.db;

import main.webapp.query.SqlCompareOp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

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
        } else if (LocalDateTime.class.isAssignableFrom(desiredType)) {
            Timestamp ts = _set.getTimestamp(columnIndex, DbContext.tzUTC); // column is TIMESTAMPTZ
            return (T)(Object)this.prepareLocalDateTime(ts);
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
        } else if (LocalDateTime.class.isAssignableFrom(desiredType)) {
            Timestamp ts = _set.getTimestamp(fieldName, DbContext.tzUTC); // column is TIMESTAMPTZ
            return (T)(Object)this.prepareLocalDateTime(ts);
        } else {
            return _set.getObject(fieldName, desiredType);
        }
    }

    private <T> T prepareLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            Instant inst = Instant.ofEpochMilli(timestamp.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(inst, DbContext.tzUTC.getTimeZone().toZoneId());
            return (T) (Object) localDateTime;
        }
    }
}
