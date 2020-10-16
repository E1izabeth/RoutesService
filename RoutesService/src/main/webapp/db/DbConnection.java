package main.webapp.db;

import main.webapp.query.StringBuilderEx;
import org.postgresql.Driver;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbConnection implements AutoCloseable {

    private final Connection _cnn;

    public final String userName;

    public DbConnection(String dbHost, int dbPort, String dbName, String login, String pwd) throws SQLException {
        this.userName = login;

        Driver driver = new Driver();

        String url = String.format("jdbc:postgresql://%1$s:%2$d/%3$s", dbHost, dbPort, dbName);
        Properties props = new Properties();
        props.setProperty("user", login);
        props.setProperty("password", pwd);
        // props.setProperty("ssl", "true");
        //_cnn = DriverManager.getConnection(url, props);
        _cnn = driver.connect(url, props);
        // _cnn.setTypeMap();

        //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        //Connection conn = DriverManager.getConnection(url);
    }

    public void createTable(DbTableInfo table) throws SQLException {
        StringBuilderEx sb = new StringBuilderEx();
        sb.formatLine("CREATE TABLE %1$s (", table.name);
        boolean isFirst = true;
        for (DbFieldInfo field : table.fields) {
            sb.append(isFirst ? "    " : "  , ");
            sb.formatLine("%1$s \t%2$s", field.name, field.dbType);
            isFirst = false;
        }
        sb.formatLine(");");
        String sql = sb.toString();

        try (PreparedStatement stmt = _cnn.prepareStatement(sql)) {
            stmt.execute();
        }
    }

    public void executeMutation(String sql, Iterable<DbQueryParameter> args) throws SQLException {
        PreparedStatement stmt = this.makeDbStatement(sql, args);
        stmt.executeUpdate();
    }

    public DbQueryResult executeQuery(String sql, DbQueryParameter... args) throws SQLException {
        return this.executeQuery(sql, Arrays.asList(args.clone()));
    }

    public DbQueryResult executeQuery(String sql, Iterable<DbQueryParameter> args) throws SQLException {
        PreparedStatement stmt = this.makeDbStatement(sql, args);
        ResultSet rs = stmt.executeQuery();
        return new DbQueryResult(stmt, rs);
    }

    private PreparedStatement makeDbStatement(String sql, Iterable<DbQueryParameter> args) throws SQLException {
        PreparedStatement stmt = _cnn.prepareStatement(sql);

        for (DbQueryParameter arg : args) {
            this.setParameter(stmt, arg.index, arg.value);
        }

        return stmt;
    }

    public <T> T compute(Class<T> type, String sql, Iterable<DbQueryParameter> args) throws SQLException {
        PreparedStatement stmt = this.makeDbStatement(sql, args);
        try (DbQueryResult rs = new DbQueryResult(stmt, stmt.executeQuery())) {
            if (rs.nextRow()) {
                T result = rs.getRowField(1, type);
                return result ;
            } else {
                throw new RuntimeException(
                    "Failed to compute scalar with query [" + sql + "] and params [" +
                    String.join(", ", StreamSupport.stream(args.spliterator(), false).map(p -> p.toString()).collect(Collectors.toList())) + "]"
                );
            }
        }
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, Object parameterObj) throws SQLException {
        if (parameterObj == null) {
            stmt.setNull(parameterIndex, java.sql.Types.OTHER);
        } else {
            if (parameterObj instanceof Byte) {
                stmt.setInt(parameterIndex, ((Byte) parameterObj).intValue());
            } else if (parameterObj instanceof String) {
                stmt.setString(parameterIndex, (String) parameterObj);
            } else if (parameterObj instanceof BigDecimal) {
                stmt.setBigDecimal(parameterIndex, (BigDecimal) parameterObj);
            } else if (parameterObj instanceof Short) {
                stmt.setShort(parameterIndex, ((Short) parameterObj).shortValue());
            } else if (parameterObj instanceof Integer) {
                stmt.setInt(parameterIndex, ((Integer) parameterObj).intValue());
            } else if (parameterObj instanceof Long) {
                stmt.setLong(parameterIndex, ((Long) parameterObj).longValue());
            } else if (parameterObj instanceof Float) {
                stmt.setFloat(parameterIndex, ((Float) parameterObj).floatValue());
            } else if (parameterObj instanceof Double) {
                stmt.setDouble(parameterIndex, ((Double) parameterObj).doubleValue());
            } else if (parameterObj instanceof byte[]) {
                stmt.setBytes(parameterIndex, (byte[]) parameterObj);
            } else if (parameterObj instanceof java.sql.Date) {
                stmt.setDate(parameterIndex, (java.sql.Date) parameterObj);
            } else if (parameterObj instanceof Time) {
                stmt.setTime(parameterIndex, (Time) parameterObj);
            } else if (parameterObj instanceof Timestamp) {
                stmt.setTimestamp(parameterIndex, (Timestamp) parameterObj);
            } else if (parameterObj instanceof Boolean) {
                stmt.setBoolean(parameterIndex, ((Boolean) parameterObj).booleanValue());
            } else if (parameterObj instanceof InputStream) {
                stmt.setBinaryStream(parameterIndex, (InputStream) parameterObj, -1);
            } else if (parameterObj instanceof java.sql.Blob) {
                stmt.setBlob(parameterIndex, (java.sql.Blob) parameterObj);
            } else if (parameterObj instanceof java.sql.Clob) {
                stmt.setClob(parameterIndex, (java.sql.Clob) parameterObj);
                //} else if (this.connection.getTreatUtilDateAsTimestamp() && parameterObj instanceof java.util.Date) {
                //    stmt.setTimestamp(parameterIndex, new Timestamp(((java.util.Date) parameterObj).getTime()));
                //} else if (parameterObj instanceof BigInteger) {
                //    stmt.setString(parameterIndex, parameterObj.toString());
                //} else {
                //    stmt.setSerializableObject(parameterIndex, parameterObj);
                //}
            } else {
                throw new RuntimeException("Unexpected Sql parameter data type " + parameterObj.getClass().getTypeName());
            }
        }
    }

    private boolean _isClosed = false;

    @Override
    public void close() throws Exception {
        if (!_isClosed) {
            _cnn.close();
            _isClosed = true;
        }
    }
}
