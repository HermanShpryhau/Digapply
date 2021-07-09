package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Identifiable;
import by.epamtc.digapply.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
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
        List<T> result = new ArrayList<>();
        try (JdbcOperator<T> jdbcOperator = new JdbcOperator<>(mapper)) {
            result = jdbcOperator.executeQuery(query);
        } catch (Exception e) {
            LOGGER.error("Error while closing JdbcOperator.", e);
            throw new DaoException("Error while closing JdbcOperator.", e);
        }
        return result;
    }

    @Override
    public List<T> findAllOnPage(long page, long count) throws DaoException {
        List<T> result = new ArrayList<>();
        try (JdbcOperator<T> jdbcOperator = new JdbcOperator<>(mapper)) {
            long startIndex = page * count;
            result = jdbcOperator.executeQuery(SELECT_ALL_ON_PAGE_QUERY, tableName, startIndex, count);
        } catch (Exception e) {
            LOGGER.error("Error while closing JdbcOperator.", e);
            throw new DaoException("Error while closing JdbcOperator.", e);
        }
        return result;
    }

    @Override
    public void remove(T entity) throws DaoException {
        throwExceptionIfNull(entity);
        removeById(entity.getId());
    }

    protected void throwExceptionIfNull(T entity) throws DaoException {
        if (entity == null) {
            LOGGER.error("Entity parameter must not be null");
            throw new DaoException("Entity parameter must not be null");
        }
    }
}
