package main.webapp.db;

import java.util.HashMap;

public class DbTypesMapping {
    private final HashMap<Class, String> _dbTypeByRuntime = new HashMap<>();

    public void put(Class type, String dbType) {
        _dbTypeByRuntime.put(type, dbType);
    }

    public String get(Class type) {
        return _dbTypeByRuntime.get(type);
    }
}
