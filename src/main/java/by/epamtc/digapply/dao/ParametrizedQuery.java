package by.epamtc.digapply.dao;

/**
 * Wrapper for SQL-query with parameters.
 */
public class ParametrizedQuery {
    /**
     * SQL-query with placeholders for parameters in {@link java.sql.PreparedStatement}.
     */
    private String query;

    /**
     * Parameters to insert into query.
     */
    private Object[] params;

    public ParametrizedQuery(String query, Object[] params) {
        this.query = query;
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public Object[] getParams() {
        return params;
    }
}
