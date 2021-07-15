package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.Role;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.validator.UserDataValidator;
import by.epamtc.digapply.validator.factory.DataValidatorFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User login(String email, String password) throws ServiceException {
        if (!validateSignInData(email, password)) {
            return null;
        }

        User user = null;
        try {
            User userFromDB = retrieveUserByEmail(email);
            if (userFromDB != null && isPasswordValid(userFromDB, password)) {
                user = userFromDB;
            }
        } catch (DaoException e) {
            logger.error("Unable to retrieve user from DB.", e);
            throw new ServiceException("Unable to retrieve user from DB.", e);
        }

        return user;
    }

    private boolean validateSignInData(String email, String password) {
        UserDataValidator validator = DataValidatorFactory.getInstance().getUserDataValidator();
        return validator.validatePassword(password) && validator.validateEmail(email);
    }

    private User retrieveUserByEmail(String email) throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        return userDao.findByEmail(email);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.getPassword().equals(DigestUtils.sha256Hex(password));
    }

    @Override
    public boolean register(String firstName, String lastName, String email, String password) throws ServiceException {
        if (!validateSignUpData(firstName, lastName, email, password)) {
            return false;
        }

        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setRoleId(Role.USER.getId());

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            userDao.save(user);
        } catch (DaoException e) {
            logger.error("Unable to save new user to Data Source.", e);
            throw new ServiceException("Unable to save new user to Data Source.", e);
        }
        return true;
    }

    private boolean validateSignUpData(String firstName, String lastName, String email, String password) {
        UserDataValidator validator = DataValidatorFactory.getInstance().getUserDataValidator();
        return validator.validateName(firstName) &&
                validator.validateName(lastName) &&
                validator.validatePassword(password) &&
                validator.validateEmail(email);
    }
}
