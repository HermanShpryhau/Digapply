package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.mapper.RowMapper;
import by.epamtc.digapply.mapper.UserRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final RowMapper<User> mapper = new UserRowMapper();

    private static final String ADD_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE user_id=?";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM Users WHERE email=?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM Users";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET email=?, password=?, name=?, surname=?, role_id=? WHERE user_id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM Users WHERE user_id=?";

    public UserDaoImpl() {
    }

    @Override
    public void addUser(User user) throws DaoException {
        checkUserParameter(user);

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = POOL.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ADD_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setLong(5, user.getRoleId());
            statement.executeUpdate();
            connection.commit();
        } catch (ConnectionPoolException e) {
            attemptRollback(connection);
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            attemptRollback(connection);
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            resetAutoCommit(connection);

            try {
                POOL.releaseConnection(connection, statement);
            } catch (ConnectionPoolException e) {
                LOGGER.error("Unable to release connection.", e);
                throw new DaoException("Unable to release connection.", e);
            }
        }
    }

    @Override
    public User getUserById(int id) throws DaoException {
        User user = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = POOL.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapper.map(resultSet);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            try {
                POOL.releaseConnection(connection, statement, resultSet);
            } catch (ConnectionPoolException e) {
                LOGGER.error("Unable to release connection.", e);
                throw new DaoException("Unable to release connection.", e);
            }
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws DaoException {
        User user = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = POOL.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapper.map(resultSet);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            try {
                POOL.releaseConnection(connection, statement, resultSet);
            } catch (ConnectionPoolException e) {
                LOGGER.error("Unable to release connection.", e);
                throw new DaoException("Unable to release connection.", e);
            }
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = POOL.getConnection();
            statement = connection.prepareStatement(GET_ALL_USERS_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(mapper.map(resultSet));
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            try {
                POOL.releaseConnection(connection, statement, resultSet);
            } catch (ConnectionPoolException e) {
                LOGGER.error("Unable to release connection.", e);
                throw new DaoException("Unable to release connection.", e);
            }
        }

        return users;
    }

    @Override
    public void updateUser(int userId, User user) throws DaoException {
        checkUserParameter(user);

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = POOL.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setLong(5, user.getRoleId());
            statement.setInt(6, userId);
            statement.executeUpdate();
            connection.commit();
        } catch (ConnectionPoolException e) {
            attemptRollback(connection);
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            attemptRollback(connection);
            LOGGER.error("Invalid SQL statement.", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            resetAutoCommit(connection);
            try {
                POOL.releaseConnection(connection, statement);
            } catch (ConnectionPoolException e) {
                LOGGER.error("Unable to release connection.", e);
                throw new DaoException("Unable to release connection.", e);
            }
        }
    }

    @Override
    public void deleteUser(User user) throws DaoException {
        checkUserParameter(user);

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = POOL.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_USER_QUERY);
            statement.setLong(1, user.getUserId());
            statement.executeUpdate();
            connection.commit();
        } catch (ConnectionPoolException e) {
            attemptRollback(connection);
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            attemptRollback(connection);
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            resetAutoCommit(connection);
            try {
                POOL.releaseConnection(connection, statement);
            } catch (ConnectionPoolException e) {
                LOGGER.error("Unable to release connection.", e);
                throw new DaoException("Unable to release connection.", e);
            }
        }
    }

    private void resetAutoCommit(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Unable to reset auto commit for transaction.", e);
            }
        }
    }

    private void checkUserParameter(User user) throws DaoException {
        if (user == null) {
            throw new DaoException("user must not be null.");
        }
    }

    private void attemptRollback(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException("Error while rolling back transaction commit.", sqlException);
            }
        }
    }
}
