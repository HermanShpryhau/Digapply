package by.epamtc.digapply.dao.mysql;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SqlUserDaoTest {
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new SqlUserDao();
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
        Optional<User> fromDB = userDao.getUserById(33);
        user.setUserId(fromDB.orElse(user).getUserId());

        assertEquals(user, fromDB.orElse(null));
    }

    @Test
    void getUserByEmailTest() throws DaoException {
        User user = new User();
        user.setEmail("germanshp8877@gmail.com");
        user.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        user.setName("Herman");
        user.setSurname("Shpryhau");
        user.setRoleId(2);
        Optional<User> fromDB = userDao.getUserByEmail("germanshp8877@gmail.com");
        user.setUserId(fromDB.orElse(user).getUserId());

        assertEquals(user, fromDB.orElse(null));
    }
}