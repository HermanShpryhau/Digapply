package by.epamtc.digapply.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final String DB_URL_PROP = "db.url";
    private static final String DB_USER_PROP = "db.user";
    private static final String DB_PASSWORD_PROP = "db.password";
    private static final String DB_DRIVER_PROP = "db.jdbc-driver";

    private BlockingQueue<Connection> pool;
    private BlockingQueue<Connection> usedConnections;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    public void init() throws ConnectionPoolException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(input);
            String dbUrl = dbProperties.getProperty(DB_URL_PROP);
            String dbUser = dbProperties.getProperty(DB_USER_PROP);
            String dbPassword = dbProperties.getProperty(DB_PASSWORD_PROP);
            Class.forName(dbProperties.getProperty(DB_DRIVER_PROP));
            pool = new ArrayBlockingQueue<>(POOL_SIZE);
            usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                pool.add(connection);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to load DB properties!", e);
            throw new ConnectionPoolException("Unable to load DB properties!", e);
        } catch (SQLException e) {
            LOGGER.error("Unable to connect to DB!", e);
            throw new ConnectionPoolException("Unable to connect to DB!", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("MySQL JDBC driver not found!", e);
            throw new ConnectionPoolException("MySQL JDBC driver not found!", e);
        }
        LOGGER.info("Connection pool initialized.");
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = pool.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Unable to connect to data source.", e);
            throw new ConnectionPoolException("Unable to connect to data source.", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        returnConnection(connection);
    }

    public void releaseConnection(Connection connection, Statement statement) throws ConnectionPoolException {
        returnConnection(connection);
        closeStatement(statement);
    }

    public void releaseConnection(Connection connection, Statement statement, ResultSet rs) throws ConnectionPoolException {
        returnConnection(connection);
        closeStatement(statement);
        closeResultSet(rs);
    }

    private void returnConnection(Connection connection) throws ConnectionPoolException {
        if (connection != null) {
            usedConnections.remove(connection);
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("Unable to release connection to data source.", e);
                throw new ConnectionPoolException("Unable to release connection to data source.", e);
            }
        }
    }

    private void closeStatement(Statement statement) throws ConnectionPoolException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Unable to close statement", e);
                throw new ConnectionPoolException("Unable to close statement", e);
            }
        }
    }

    private void closeResultSet(ResultSet rs) throws ConnectionPoolException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Unable to close result set.", e);
                throw new ConnectionPoolException("Unable to close result set.", e);
            }
        }
    }

    public void dispose() throws ConnectionPoolException {
        try {
            for (Connection connection : pool) {
                connection.close();
            }
            for (Connection connection : usedConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to close all connections.", e);
            throw new ConnectionPoolException("Unable to close all connections.", e);
        }
        LOGGER.info("Connection pool disposed.");
    }

    private static class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }
}
