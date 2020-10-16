package main.webapp.db;

import main.webapp.query.StringBuilderEx;
import main.webapp.query.parsing.Func0;

import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;

public class DbParametrizedQueryBuilder extends StringBuilderEx {

    final ArrayList<DbQueryParameter> _parameters = new ArrayList<>();

    public void appendParameter(Object value) {
        this.append(" ? ");
        _parameters.add(new DbQueryParameter(_parameters.size() + 1, value));
    }

    public DbParametrizedQueryInfo buildQuery() {
        String sql = this.toString();
        DbQueryParameter[] parameters = _parameters.toArray(new DbQueryParameter[0]);

        return new DbParametrizedQueryInfo(sql, parameters);
    }
}
