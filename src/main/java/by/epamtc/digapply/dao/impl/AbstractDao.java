package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.Dao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.JdbcOperator;
import by.epamtc.digapply.model.DataBean;
import by.epamtc.digapply.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implements methods of {@link Dao} interface that are same for all DAOs.
 * @param <T> Type of DAO's entity.
 */
public abstract class AbstractDao<T extends DataBean> implements Dao<T> {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ";
    private final String selectAllOnPageQuery;

    protected final RowMapper<T> mapper;
    protected final JdbcOperator<T> jdbcOperator;
    private final String tableName;

    protected AbstractDao(RowMapper<T> mapper, String tableName) {
        this.mapper = mapper;
        jdbcOperator = new JdbcOperator<>(this.mapper);
        this.tableName = tableName;
        selectAllOnPageQuery = "SELECT * FROM " + tableName + " LIMIT ?, ?";
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = SELECT_ALL_QUERY + tableName;
        return jdbcOperator.executeQuery(query);
    }

    @Override
    public List<T> findAllOnPage(long page, long count) throws DaoException {
        long startIndex = (page - 1) * count;
        return jdbcOperator.executeQuery(selectAllOnPageQuery, startIndex, count);
    }

    @Override
    public long getRowsCount() throws DaoException {
        return findAll().size();
    }

    @Override
    public long remove(T entity) throws DaoException {
        throwExceptionIfNull(entity);
        return removeById(entity.getId());
    }

    protected void throwExceptionIfNull(Object o) throws DaoException {
        if (o == null) {
            logger.error("Entity parameter must not be null");
            throw new DaoException("Entity parameter must not be null");
        }
    }
}
