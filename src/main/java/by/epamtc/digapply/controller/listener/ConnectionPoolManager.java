package by.epamtc.digapply.controller.listener;

import by.epamtc.digapply.dao.connection.ConnectionPool;
import by.epamtc.digapply.dao.connection.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes and disposes connection pool.
 */
public class ConnectionPoolManager implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().initialize();
        } catch (ConnectionPoolException e) {
            logger.error("Unable to initialize connection pool.", e);
            throw new RuntimeException("Unable to initialize connection pool.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.error("Unable to dispose connection pool.", e);
            throw new RuntimeException("Unable to dispose connection pool.", e);
        }
    }
}
