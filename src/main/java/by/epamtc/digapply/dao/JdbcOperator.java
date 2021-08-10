package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.connection.ConnectionPool;
import by.epamtc.digapply.dao.connection.ConnectionPoolException;
import by.epamtc.digapply.entity.Identifiable;
import by.epamtc.digapply.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes queries for DAOs of entity.
 * @param <T> DAO entity's type.
 */
public class JdbcOperator<T extends Identifiable> {
    private static final Logger logger = LogManager.getLogger();
    public static final long NO_ID = -1L;

    /**
     * Entity row mapper.
     */
    private final RowMapper<T> mapper;

    public JdbcOperator(RowMapper<T> mapper) {
        this.mapper = mapper;
    }

    /**
     * Executes query parametrized with {@code parameters}. The result is put into List of entities.
     * @param query String containing SQL query.
     * @param parameters Parameters to insert into query.
     * @return List of entities fetched by the query. If none were fetched an empty list is returned.
     */
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
        } catch (SQLException e) {
            logger.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        } catch (ConnectionPoolException e) {
            logger.error("Unable to retrieve connection.", e);
            // TODO Check if there is need to throw exception
            throw new DaoException("Unable to retrieve connection.", e);
        }
        return result;
    }

    /**
     * Executes query for only one resulting entity.
     * @param query String containing SQL query.
     * @param parameters Parameters to insert into query.
     * @return Fetched entity or null if nothing was fetched by the query.
     */
    public T executeSingleEntityQuery(String query, Object... parameters) throws DaoException {
        return executeQuery(query, parameters)
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Executes updates on DB.
     * @param query String containing SQL query.
     * @param parameters Parameters to insert into query.
     * @return Either generated PK or number of affected rows.
     */
    public long executeUpdate(String query, Object... parameters) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);
            long rowsAffected = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                return rowsAffected;
            }
        } catch (SQLException e) {
            logger.error("Unable to execute update query.", e);
            throw new DaoException("Unable to execute update query.", e);
        } catch (ConnectionPoolException e) {
            logger.error("Unable to retrieve connection.", e);
            throw new DaoException("Unable to retrieve connection.", e);
        }
    }

    /**
     * Executes several updates in transaction mode. If any of them fails to execute the changes are reverted.
     * @param queries List of queries that are to be executed as transaction.
     * @return PK of row affected by first update/
     */
    public long executeTransactionalUpdate(List<ParametrizedQuery> queries) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            connection.setAutoCommit(false);
            long firstQueryGeneratedKey = executeUpdates(queries, connection);
            connection.commit();
            return firstQueryGeneratedKey;
        } catch (SQLException e) {
            attemptRollback(connection);
            logger.error("Unable to execute update query.", e);
            throw new DaoException("Unable to execute update query.", e);
        } catch (ConnectionPoolException e) {
            logger.error("Unable to retrieve connection.", e);
            throw new DaoException("Unable to retrieve connection.", e);
        } finally {
            attemptConnectionRelease(connection);
        }
    }

    private long executeUpdates(List<ParametrizedQuery> queries, Connection connection) throws SQLException {
        long firstQueryGeneratedKey = NO_ID;
        boolean idSet = false;
        for (ParametrizedQuery query : queries) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(query.getParams());
            PreparedStatement statement = connection.prepareStatement(query.getQuery(), Statement.RETURN_GENERATED_KEYS);
            parameterSetter.setValues(statement);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!idSet && generatedKeys != null && generatedKeys.next()) {
                firstQueryGeneratedKey = generatedKeys.getLong(1);
                idSet = true;
            }
        }
        return firstQueryGeneratedKey;
    }

    private void attemptRollback(Connection connection) throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException sqlException) {
            logger.error("Unable to rollback commit.", sqlException);
            throw new DaoException(sqlException);
        }
    }

    private void attemptConnectionRelease(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("Unable to return connection to connection pool.", e);
                throw new DaoException("Unable to return connection to connection pool.", e);
            }
        }
    }
}
