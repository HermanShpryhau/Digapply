package by.epamtc.digapply.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementParameterSetter {
    private final Object[] parameters;

    public PreparedStatementParameterSetter(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setValues(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            for (int i = 1; i <= parameters.length; i++) {
                statement.setObject(i, parameters[i - 1]);
            }
        }
    }
}
