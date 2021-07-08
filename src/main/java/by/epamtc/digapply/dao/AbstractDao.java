package by.epamtc.digapply.dao;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.entity.Identifiable;
import by.epamtc.digapply.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ";
    private static final String SELECT_ALL_ON_PAGE_QUERY = "SELECT * FROM ? LIMIT ?, ?";

    protected final RowMapper<T> mapper;
    private final String tableName;

    protected AbstractDao(RowMapper<T> mapper, String tableName) {
        this.mapper = mapper;
        this.tableName = tableName;
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = SELECT_ALL_QUERY + tableName;
        JdbcOperator<T> jdbcOperator = new JdbcOperator<>(mapper);
        return jdbcOperator.executeQuery(query);
    }

    @Override
    public List<T> findAllOnPage(long page, long count) throws DaoException {
        long startIndex = page * count;
        JdbcOperator<T> jdbcOperator = new JdbcOperator<>(mapper);
        return jdbcOperator.executeQuery(SELECT_ALL_ON_PAGE_QUERY, tableName, startIndex, count);
    }

    @Override
    public void remove(T entity) throws DaoException {
        throwExceptionIfNull(entity);
        removeById(entity.getId());
    }

    protected void attemptConnectionRelease(Connection connection) throws DaoException {
        try {
            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to release connection.", e);
            throw new DaoException("Unable to release connection.", e);
        }
    }

    protected void throwExceptionIfNull(T entity) throws DaoException {
        if (entity == null) {
            LOGGER.error("Entity parameter must not be null");
            throw new DaoException("Entity parameter must not be null");
        }
    }
}
