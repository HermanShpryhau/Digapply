package by.epamtc.digapply.dao;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.entity.Identifiable;
import by.epamtc.digapply.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcOperator<T extends Identifiable> {
    private static final Logger logger = LogManager.getLogger();

    private final RowMapper<T> mapper;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public JdbcOperator(RowMapper<T> mapper) {
        this.mapper = mapper;
    }

    public List<T> executeQuery(String query, Object... parameters) throws DaoException {
        List<T> result = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection();
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
            throw new DaoException("Unable to retrieve connection.", e);
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
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            PreparedStatementParameterSetter parameterSetter = new PreparedStatementParameterSetter(parameters);
            parameterSetter.setValues(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to execute query.", e);
            throw new DaoException("Unable to execute query.", e);
        } catch (ConnectionPoolException e) {
            logger.error("Unable to retrieve connection.", e);
            throw new DaoException("Unable to retrieve connection.", e);
        }
    }
}
