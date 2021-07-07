package by.epamtc.digapply.dao.mysql;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.dao.impl.UserDaoImpl;
import by.epamtc.digapply.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SqlUserDaoTest {
    UserDao userDao;

    @BeforeAll
    static void setUpConnectionPool() throws ConnectionPoolException {
        ConnectionPool.getInstance().init();
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    void getUserByIdTest() throws DaoException {
        User user = new User();
        user.setUserId(33);
        user.setEmail("germanshp8877@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        User fromDB = userDao.getUserById(33);
        if (fromDB != null) {
            user.setUserId(fromDB.getUserId());
        }

        assertEquals(user, fromDB);
    }

    @Test
    void getUserByEmailTest() throws DaoException {
        User user = new User();
        user.setEmail("germanshp8877@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        User fromDB = userDao.getUserByEmail("germanshp8877@gmail.com");
        if (fromDB != null) {
            user.setUserId(fromDB.getUserId());
        }

        assertEquals(user, fromDB);
    }
}