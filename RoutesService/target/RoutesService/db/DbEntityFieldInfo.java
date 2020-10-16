package main.webapp.db;

import java.lang.reflect.Field;

public class DbEntityFieldInfo extends DbFieldInfo {
    public final Field runtimeField;
    public final boolean isDbGenerated;
    public final boolean isReadOnly;

    public DbEntityFieldInfo(String name, String dbType, Field runtimeField, boolean isDbGenerated, boolean isReadOnly) {
        super(name, dbType, runtimeField.getType());
        this.runtimeField = runtimeField;
        this.isDbGenerated = isDbGenerated;
        this.isReadOnly = isReadOnly;
    }
}
