package by.epamtc.digapply.connection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolTest {
    @BeforeAll
    static void setUpConnectionPool() throws ConnectionPoolException {
        ConnectionPool.getInstance().initialize();
    }

    @Test
    void getInstanceSingleThreadTest() {
        ConnectionPool pool1 = ConnectionPool.getInstance();
        ConnectionPool pool2 = ConnectionPool.getInstance();

        assertEquals(pool1, pool2);
    }

    @Test
    void getInstanceMultiThreadTest() throws InterruptedException {
        ConnectionPool pool1 = ConnectionPool.getInstance();
        Thread t1 = new Thread(() -> {
            ConnectionPool pool2 = ConnectionPool.getInstance();
            assertEquals(pool1, pool2);
        });

        t1.start();
        t1.join();
    }

    @Test
    void getConnectionTest() throws ConnectionPoolException, SQLException {
        assertTrue(ConnectionPool.getInstance().takeConnection().isValid(10));
    }
}