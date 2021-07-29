package by.epamtc.digapply.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Opens and stores connections to database. Thread-safe.
 */
public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final String DB_URL_PROP = "db.url";
    private static final String DB_USER_PROP = "db.user";
    private static final String DB_PASSWORD_PROP = "db.password";
    private static final String DB_DRIVER_PROP = "db.jdbc-driver";

    private BlockingQueue<ProxyConnection> pool;
    private BlockingQueue<ProxyConnection> usedConnections;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    public void initialize() throws ConnectionPoolException {
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
                pool.add(new ProxyConnection(connection));
            }
        } catch (IOException e) {
            logger.error("Unable to load DB properties!", e);
            throw new ConnectionPoolException("Unable to load DB properties!", e);
        } catch (SQLException e) {
            logger.error("Unable to connect to DB!", e);
            throw new ConnectionPoolException("Unable to connect to DB!", e);
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC driver not found!", e);
            throw new ConnectionPoolException("MySQL JDBC driver not found!", e);
        }
        logger.info("Connection pool initialized.");
    }

    public Connection takeConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = pool.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Unable to retrieve connection to data source.", e);
            throw new ConnectionPoolException("Unable to retrieve connection to data source.", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection != null) {
            usedConnections.remove(connection);
            try {
                pool.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Unable to release connection to data source.", e);
                throw new ConnectionPoolException("Unable to release connection to data source.", e);
            }
        }
    }

    public void dispose() throws ConnectionPoolException {
        try {
            for (ProxyConnection connection : pool) {
                connection.reallyClose();
            }
            for (ProxyConnection connection : usedConnections) {
                connection.reallyClose();
            }
        } catch (SQLException e) {
            logger.error("Unable to close all connections.", e);
            throw new ConnectionPoolException("Unable to close all connections.", e);
        }
        logger.info("Connection pool disposed.");
    }

    private static class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }
}
