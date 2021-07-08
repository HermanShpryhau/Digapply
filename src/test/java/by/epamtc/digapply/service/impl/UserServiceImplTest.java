package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserService userService;

    @BeforeAll
    static void setUpConnectionPool() throws ConnectionPoolException {
        ConnectionPool.getInstance().initialize();
    }

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void loginExistingUserTest() throws ServiceException {
        User user = userService.login("kirk@mail.com", "jameskirk");
        assertEquals("kirk@mail.com", user.getEmail());
    }

    @Test
    void loginExistingUserWithWrongPasswordTest() throws ServiceException {
        User user = userService.login("kirk@mail.com", "123");
        assertNull(user);
    }

    @Test
    void loginNonExistingUserTest() throws ServiceException {
        User user = userService.login("123@mail.com", "123");
        assertNull(user);
    }

    @Test
    void loginWithNullEmailTest() throws ServiceException {
        User user = userService.login(null, "123");
        assertNull(user);
    }

    @Test
    void loginWithNullPasswordTest() throws ServiceException {
        User user = userService.login("123@mail.com", null);
        assertNull(user);
    }

    @Test
    void loginWithNullParamsTest() throws ServiceException {
        User user = userService.login(null, null);
        assertNull(user);
    }
}