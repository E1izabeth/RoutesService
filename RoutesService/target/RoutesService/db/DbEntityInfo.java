package main.webapp.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbEntityInfo extends DbTableInfo {
    public final Class runtimeType;
    public final List<DbEntityFieldInfo> entityFields;

    public DbEntityInfo(String name, Class runtimeType, DbEntityFieldInfo... fields) {
        this(name, runtimeType, Arrays.asList(fields.clone()));
    }

    public DbEntityInfo(String name, Class runtimeType, Iterable<DbEntityFieldInfo> fields) {
        super(name, StreamSupport.stream(fields.spliterator(), false).collect(Collectors.toList()));
        this.runtimeType = runtimeType;
        this.entityFields = Collections.unmodifiableList(StreamSupport.stream(fields.spliterator(), false).collect(Collectors.toList()));
    }
}
