package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Identifiable;
import by.epamtc.digapply.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcOperator<T extends Identifiable> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Connection connection;
    private final RowMapper<T> mapper;

    public JdbcOperator(Connection connection, RowMapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    public List<T> executeQuery(String query, Object... parameters) throws DaoException {
        List<T> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);

            startTransaction();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                result.add(entity);
            }
            endTransaction();
        } catch (SQLException e) {
            attemptRollback();
            LOGGER.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        }

        return result;
    }

    public void executeUpdate(String query, Object... parameters) throws  DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);

            startTransaction();
            statement.executeUpdate();
            endTransaction();
        } catch (SQLException e) {
            attemptRollback();
            LOGGER.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        }
    }

    private void startTransaction() throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("Unable to set auto commit to false.", e);
                throw new DaoException("Unable to set auto commit to false.", e);
            }
        }
    }

    private void endTransaction() throws DaoException {
        if (connection != null) {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Unable to commit transaction.", e);
                throw new DaoException("Unable to commit transaction.", e);
            }
        }
    }

    private void attemptRollback() throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Unable tp rollback transaction commit.", e);
                throw new DaoException("Unable tp rollback transaction commit.", e);
            }
        }
    }
}
