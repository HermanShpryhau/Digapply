package by.epamtc.digapply.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private BlockingQueue<Connection> pool;
    private BlockingQueue<Connection> usedConnections;

    private final static int POOL_SIZE = 10;
    private final static Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    private ConnectionPool() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(input);
            String dbUrl = dbProperties.getProperty("db.url");
            String dbUser = dbProperties.getProperty("db.user");
            String dbPassword = dbProperties.getProperty("db.password");
            Class.forName(dbProperties.getProperty("db.jdbc-driver"));
            pool = new ArrayBlockingQueue<>(POOL_SIZE);
            usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                pool.add(connection);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to load DB properties!", e);
        } catch (SQLException e) {
            LOGGER.error("Unable to connect to DB!", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("MySQL JDBC driver not found!", e);
        }
    }

    public void init() {
        try (InputStream input = ConnectionPool.class.getResourceAsStream("db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(input);
            String dbUrl = dbProperties.getProperty("db.url");
            String dbUser = dbProperties.getProperty("db.user");
            String dbPassword = dbProperties.getProperty("db.password");
            Class.forName(dbProperties.getProperty("db.jdbc-driver"));
            pool = new ArrayBlockingQueue<>(POOL_SIZE);
            usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                pool.add(connection);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to load DB properties!", e);
        } catch (SQLException e) {
            LOGGER.error("Unable to connect to DB!", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("MySQL JDBC driver not found!", e);
        }
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = pool.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Unable to connect to data source.", e);
            throw new ConnectionPoolException("Unable to connect to data source.", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            pool.offer(connection);
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
    }
}