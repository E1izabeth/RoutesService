package main.webapp.query;

public class QueryFieldInfo {
    public final String name;
    public final String sqlViewName;
    public final SqlType type;

    public QueryFieldInfo(String name, String sqlViewName, SqlType type) {
        this.name = name;
        this.sqlViewName = sqlViewName;
        this.type = type;
    }
}
