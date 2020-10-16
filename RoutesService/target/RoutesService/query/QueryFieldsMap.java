package main.webapp.query;

import main.webapp.query.parsing.QueryParsingException;

import java.util.HashMap;

public class QueryFieldsMap {
    private final HashMap<String, QueryFieldInfo> _fieldsByName = new HashMap<>();

    public void put(String name, String sqlViewName, SqlType type) {
        _fieldsByName.put(name, new QueryFieldInfo(name, sqlViewName, type));
    }

    public QueryFieldInfo get(String name) {
        QueryFieldInfo result = _fieldsByName.get(name);
        if (result == null)
            throw new QueryParsingException("Unknown query field name " + name);

        return result;
    }
}
