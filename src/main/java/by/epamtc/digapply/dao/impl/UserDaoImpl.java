package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.dao.AbstractDao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.JdbcOperator;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.mapper.factory.RowMapperFactory;
import by.epamtc.digapply.resource.Tables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private static final String ADD_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE user_id=?";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM Users WHERE email=?";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET email=?, password=?, name=?, surname=?, role_id=? WHERE user_id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM Users WHERE user_id=?";

    public UserDaoImpl() {
        super(RowMapperFactory.getInstance().getUserRowMapper(), Tables.USER_TABLE);
    }

    @Override
    public void add(User user) throws DaoException {
        throwExceptionIfNull(user);

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            JdbcOperator<User> jdbcOperator = new JdbcOperator<>(connection, mapper);
            jdbcOperator.executeUpdate(
                    ADD_USER_QUERY,
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getSurname(),
                    user.getRoleId()
            );
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } finally {
            attemptConnectionRelease(connection);
        }
    }

    @Override
    public User findById(long id) throws DaoException {
        User user = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            JdbcOperator<User> jdbcOperator = new JdbcOperator<>(connection, mapper);
            user = jdbcOperator.executeQuery(FIND_USER_BY_ID_QUERY, id)
                    .stream()
                    .findFirst()
                    .orElse(null);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } finally {
            attemptConnectionRelease(connection);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        User user = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            JdbcOperator<User> jdbcOperator = new JdbcOperator<>(connection, mapper);
            user = jdbcOperator.executeQuery(FIND_USER_BY_EMAIL_QUERY, email)
                    .stream()
                    .findFirst()
                    .orElse(null);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } finally {
            attemptConnectionRelease(connection);
        }

        return user;
    }

    @Override
    public void updateEntity(User entity) throws DaoException {
        throwExceptionIfNull(entity);

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            JdbcOperator<User> jdbcOperator = new JdbcOperator<>(connection, mapper);
            jdbcOperator.executeUpdate(
                    UPDATE_USER_QUERY,
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getName(),
                    entity.getSurname(),
                    entity.getRoleId(),
                    entity.getId()
            );
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } finally {
            attemptConnectionRelease(connection);
        }
    }


    @Override
    public void removeById(long id) throws DaoException {
        Connection connection = null;

        try {
            connection = POOL.getConnection();
            JdbcOperator<User> jdbcOperator = new JdbcOperator<>(connection, mapper);
            jdbcOperator.executeUpdate(
                    DELETE_USER_QUERY,
                    id
            );
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } finally {
            attemptConnectionRelease(connection);
        }
    }
}
