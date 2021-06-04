package by.epamtc.digapply.dao.mysql;

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
import java.util.Optional;

public class SqlUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(SqlUserDao.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final RowMapper<User> mapper = new UserRowMapper();

    public SqlUserDao() {}

    @Override
    public void addUser(User user)  throws DaoException {
        if (user == null) {
            throw new DaoException("user must not be null.");
        }

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            String query = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setInt(5, user.getRoleId());
            statement.executeUpdate();
            statement.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            POOL.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> getUserById(int id) throws DaoException {
        User user = null;

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            String query = "SELECT * FROM Users WHERE user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapper.map(resultSet);
            }
            statement.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            POOL.releaseConnection(connection);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws DaoException {
        User user = null;

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            String query = "SELECT * FROM Users WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapper.map(resultSet);
            }
            statement.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            POOL.releaseConnection(connection);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            String query = "SELECT * FROM Users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(mapper.map(resultSet));
            }
            statement.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            POOL.releaseConnection(connection);
        }

        return users;
    }

    @Override
    public void updateUser(int userId, User user) throws DaoException {
        if (user == null) {
            throw new DaoException("user must not be null.");
        }

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            String query = "UPDATE Users " +
                    "SET email=?, password=?, name=?, surname=?, role_id=? " +
                    "WHERE user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setInt(5, user.getRoleId());
            statement.setInt(6, userId);
            statement.executeUpdate();
            statement.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement.", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            POOL.releaseConnection(connection);
        }
    }

    @Override
    public void deleteUser(User user) throws DaoException {
        if (user == null) {
            throw new DaoException("user must not be null.");
        }

        Connection connection = null;
        try {
            connection = POOL.getConnection();
            String query = "DELETE FROM Users " +
                    "WHERE user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getUserId());
            statement.executeUpdate();
            statement.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Unable to retrieve DB connection", e);
            throw new DaoException("Unable to retrieve DB connection", e);
        } catch (SQLException e) {
            LOGGER.error("Invalid SQL statement", e);
            throw new DaoException("Invalid SQL statement", e);
        } finally {
            POOL.releaseConnection(connection);
        }
    }
}
