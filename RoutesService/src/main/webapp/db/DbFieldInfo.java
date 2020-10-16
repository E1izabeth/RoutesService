package main.webapp.db;

public class DbFieldInfo {
    public final String name;
    public final String dbType;
    public final Class runtimeType;

    public DbFieldInfo(String name, String dbType, Class runtimeType) {
        this.name = name;
        this.dbType = dbType;
        this.runtimeType = runtimeType;
    }
}
