package dev.shph.digapply.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Sets parameters in {@link PreparedStatement}.
 */
public class PreparedStatementParameterSetter {
    private final Object[] parameters;

    public PreparedStatementParameterSetter(Object[] parameters) {
        this.parameters = parameters;
    }

    /**
     * Inserts parameters into prepared statement.
     * @param statement Statement to insert parameters to.
     */
    public void setValues(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            for (int i = 1; i <= parameters.length; i++) {
                statement.setObject(i, parameters[i - 1]);
            }
        }
    }
}
