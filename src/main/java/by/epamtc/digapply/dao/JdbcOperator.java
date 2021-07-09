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

public class JdbcOperator<T extends Identifiable> implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();

    private final RowMapper<T> mapper;
    private Connection connection;

    public JdbcOperator(RowMapper<T> mapper) throws DaoException {
        this.mapper = mapper;
        try {
            this.connection = ConnectionPool.getInstance().takeConnection();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        }
    }

    public List<T> executeQuery(String query, Object... parameters) throws DaoException {
        checkConnection();

        List<T> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                result.add(entity);
            }
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
        checkConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)){
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        }
    }

    private void checkConnection() throws DaoException{
        if (connection == null) {
            throw new DaoException("You cannot use JdbcOperator after closing it.");
        }
    }

    public void startTransaction() throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("Unable to set auto commit to false.", e);
                throw new DaoException("Unable to set auto commit to false.", e);
            }
        }
    }

    public void endTransaction() throws DaoException {
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

    public void attemptRollback() throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Unable tp rollback transaction commit.", e);
                throw new DaoException("Unable tp rollback transaction commit.", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
        this.connection = null;
    }
}
