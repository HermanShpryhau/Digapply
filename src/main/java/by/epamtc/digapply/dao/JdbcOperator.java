package by.epamtc.digapply.dao;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
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

    private final RowMapper<T> mapper;

    public JdbcOperator(RowMapper<T> mapper) {
        this.mapper = mapper;
    }

    public List<T> executeQuery(String query, Object... parameters) throws DaoException {
        List<T> result = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                result.add(entity);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        }

        return result;
    }

    public T executeSingleEntityQuery(String query, Object... parameters) throws DaoException {
        return executeQuery(query, parameters)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void executeUpdate(String query, Object... parameters) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(query);
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);

            startTransaction(connection);
            statement.executeUpdate();
            endTransaction(connection);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            attemptRollback(connection);
            LOGGER.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Unable to close statement and release connection.", e);
                throw new DaoException("Unable to close statement and release connection.", e);
            }
        }
    }

    private void startTransaction(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("Unable to set auto commit to false.", e);
                throw new DaoException("Unable to set auto commit to false.", e);
            }
        }
    }

    private void endTransaction(Connection connection) throws DaoException {
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

    private void attemptRollback(Connection connection) throws DaoException {
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
