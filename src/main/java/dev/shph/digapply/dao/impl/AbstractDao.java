package dev.shph.digapply.dao.impl;

import dev.shph.digapply.dao.Dao;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.PreparedStatementParameterSetter;
import dev.shph.digapply.entity.Identifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Implements methods of {@link Dao} interface that are same for all DAOs.
 * @param <T> Type of DAO's entity.
 */
public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ";
    private final String selectAllOnPageQuery;

    @Autowired
    protected RowMapper<T> mapper;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected DataSource dataSource;

    private final String tableName;

    protected AbstractDao(String tableName) {
        this.tableName = tableName;
        selectAllOnPageQuery = "SELECT * FROM " + tableName + " LIMIT ?, ?";
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = SELECT_ALL_QUERY + tableName;
        return jdbcTemplate.query(query, mapper);
    }

    @Override
    public List<T> findAllOnPage(long page, long count) {
        long startIndex = (page - 1) * count;
        PreparedStatementParameterSetter parameterSetter
                = new PreparedStatementParameterSetter(new Object[]{startIndex, count});
        return jdbcTemplate.query(selectAllOnPageQuery, parameterSetter::setValues, mapper);
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
