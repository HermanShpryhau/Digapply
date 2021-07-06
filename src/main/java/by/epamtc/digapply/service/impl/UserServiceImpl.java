package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        if (email == null || password == null) {
            return Optional.empty();
        }

        Optional<User> user = Optional.empty();
        try {
            Optional<User> userFromDB = retrieveUserByEmail(email);
            if (userFromDB.isPresent() && isPasswordValid(userFromDB.get(), password)) {
                user = userFromDB;
            }
        } catch (DaoException e) {
            LOGGER.error("Unable to retrieve user from DB.", e);
            throw new ServiceException("Unable to retrieve user from DB.", e);
        }

        return user;
    }

    private Optional<User> retrieveUserByEmail(String email) throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        return userDao.getUserByEmail(email);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.getPassword().equals(DigestUtils.sha256Hex(password));
    }
}
