package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoImplTest {
    UserDao userDao = new UserDaoImpl();

    @BeforeAll
    static void setUpConnectionPool() throws ConnectionPoolException {
        ConnectionPool.getInstance().initialize();
    }

    @AfterAll
    static void tearDown() throws ConnectionPoolException {
        ConnectionPool.getInstance().dispose();
    }

    @Test
    @Order(1)
    void saveTest() throws DaoException {
        User user = new User();
        user.setEmail("germanshp8877@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        userDao.save(user);
        User fromDB = userDao.findByEmail("germanshp8877@gmail.com");
        user.setUserId(fromDB.getId());
        assertEquals(user, fromDB);
    }

    @Test
    @Order(2)
    void findUserByIdTest() throws DaoException {
        User user = new User();
        user.setUserId(33);
        user.setEmail("germanshp8877@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        User fromDB = userDao.findByEmail("germanshp8877@gmail.com");
        if (fromDB != null) {
            user.setUserId(fromDB.getUserId());
        }

        assertEquals(user, fromDB);
    }

    @Test
    @Order(3)
    void findUserByEmailTest() throws DaoException {
        User user = new User();
        user.setEmail("germanshp8877@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        User fromDB = userDao.findByEmail("germanshp8877@gmail.com");
        if (fromDB != null) {
            user.setUserId(fromDB.getUserId());
        }
        assertEquals(user, fromDB);
    }

    @Test
    @Order(4)
    void updateEntityTest() throws DaoException {
        User user = new User();
        user.setEmail("germanshp887@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        User fromDB = userDao.findByEmail("germanshp8877@gmail.com");
        if (fromDB != null) {
            user.setUserId(fromDB.getUserId());
        }
        userDao.updateEntity(user);
        fromDB = userDao.findById(user.getId());
        assertEquals(user, fromDB);
    }

    @Test
    @Order(5)
    void removeByIdTest() throws DaoException {
        User fromDB = userDao.findByEmail("germanshp887@gmail.com");
        if (fromDB != null) {
            userDao.removeById(fromDB.getUserId());
        }
        assertNull(userDao.findByEmail("germanshp887@gmail.com"));
    }
}