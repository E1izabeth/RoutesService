package main.webapp.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class DbParametrizedQueryInfo {
    public final String sql;
    public final List<DbQueryParameter> parameters;

    public DbParametrizedQueryInfo(String sql, Object...  parameters) {
        this(sql, IntStream.range(0, parameters.length).mapToObj(n -> new DbQueryParameter(n + 1, parameters[n])).collect(Collectors.toList()));
    }

    public DbParametrizedQueryInfo(String sql, DbQueryParameter...  parameters) {
        this(sql, Arrays.asList( parameters.clone()));
    }

    public DbParametrizedQueryInfo(String sql, Iterable<DbQueryParameter> parameters) {
        this.sql = sql;
        this.parameters = Collections.unmodifiableList(StreamSupport.stream(parameters.spliterator(), false).collect(Collectors.toList()));
    }
}
