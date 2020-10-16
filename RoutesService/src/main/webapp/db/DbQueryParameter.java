package main.webapp.db;

import java.sql.SQLType;

public class DbQueryParameter {
    public final int index;
    public final Object value;

    public DbQueryParameter(int index, Object value) {
        this.index = index;
        this.value = value;
    }
}
