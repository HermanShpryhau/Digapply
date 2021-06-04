package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserService userService;

    @BeforeAll
    static void setUpConnectionPool() throws ConnectionPoolException {
        ConnectionPool.getInstance().init();
    }

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void loginExistingUserTest() throws ServiceException {
        Optional<User> user = userService.login("kirk@mail.com", "jameskirk");
        assertEquals("kirk@mail.com", user.get().getEmail());
    }

    @Test
    void loginExistingUserWithWrongPasswordTest() throws ServiceException {
        Optional<User> user = userService.login("kirk@mail.com", "123");
        assertFalse(user.isPresent());
    }

    @Test
    void loginNonExistingUserTest() throws ServiceException {
        Optional<User> user = userService.login("123@mail.com", "123");
        assertFalse(user.isPresent());
    }

    @Test
    void loginWithNullEmailTest() throws ServiceException {
        Optional<User> user = userService.login(null, "123");
        assertFalse(user.isPresent());
    }

    @Test
    void loginWithNullPasswordTest() throws ServiceException {
        Optional<User> user = userService.login("123@mail.com", null);
        assertFalse(user.isPresent());
    }

    @Test
    void loginWithNullParamsTest() throws ServiceException {
        Optional<User> user = userService.login(null, null);
        assertFalse(user.isPresent());
    }
}