package main.webapp.db;

public class DbSchemaTableEntity {
    public String schemaname, tablename, tableowner, tablespace;
    public boolean hasindexes, hasrules, hastriggers, rowsecurity;
}
