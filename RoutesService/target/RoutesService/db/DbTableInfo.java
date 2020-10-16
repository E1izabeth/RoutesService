package main.webapp.db;

import main.webapp.query.parsing.Expr;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbTableInfo {
    public final String name;
    public final List<DbFieldInfo> fields;

    public DbTableInfo(String name, DbFieldInfo... fields) {
        this(name, Arrays.asList(fields.clone()));
    }

    public DbTableInfo(String name, Iterable<DbFieldInfo> fields) {
        if (name == null)
            throw new IllegalArgumentException();
        if (fields == null)
            throw new IllegalArgumentException();

        this.name = name;
        this.fields = Collections.unmodifiableList(StreamSupport.stream(fields.spliterator(), false).collect(Collectors.toList()));
    }
}
