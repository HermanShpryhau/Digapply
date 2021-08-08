package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.*;
import by.epamtc.digapply.entity.RoleEnum;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.entity.dto.UserDto;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.validation.EntityValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final int MINIMAL_AFFECTED_ROWS = 1;

    @Override
    public User login(String email, String password) throws ServiceException {
        if (email == null || password == null) {
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

    private User retrieveUserByEmail(String email) throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        return userDao.findByEmail(email);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.getPassword().equals(DigestUtils.sha256Hex(password));
    }

    @Override
    public boolean register(String firstName, String lastName, String email, String password) throws ServiceException {
        User user = buildUser(firstName, lastName, email, password);
        if (isUserEntityValid(user)) {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            try {
                userDao.save(user);
            } catch (DaoException e) {
                logger.error("Unable to save new user to Data Source.", e);
                throw new ServiceException("Unable to save new user to Data Source.", e);
            }
            return true;
        } else {
            return false;
        }
    }

    private User buildUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setRoleId(RoleEnum.USER.getId());
        return user;
    }

    private boolean isUserEntityValid(User user) {
        EntityValidator<User> userEntityValidator = ValidatorFactory.getInstance().getUserDataValidator();
        return userEntityValidator.validate(user);
    }

    @Override
    public String getFullNameById(long userId) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            User user = userDao.findById(userId);
            if (user == null) {
                return null;
            }
            return user.getName() + ' ' + user.getSurname();
        } catch (DaoException e) {
            logger.error("Unable to fetch user by id", e);
            throw new ServiceException("Unable to fetch user by id", e);
        }
    }

    @Override
    public List<UserDto> retrieveAllUsersAsDto() throws ServiceException {
        List<UserDto> dtos = new ArrayList<>();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            List<User> users = userDao.findAll();
            for (User user : users) {
                dtos.add(buildUserDto(user));
            }
        } catch (DaoException e) {
            logger.error("Unable to retrieve all users.", e);
            throw new ServiceException("Unable to retrieve all users.", e);
        }
        return dtos;
    }

    UserDto buildUserDto(User user) throws DaoException {
        UserDto dto = new UserDto();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setRoleId(user.getRoleId());
        RoleDao roleDao = DaoFactory.getInstance().getRoleDao();
        dto.setRole(roleDao.findById(user.getRoleId()).getRoleName());
        return dto;
    }

    @Override
    public boolean deleteUser(long id) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            long rowsAffected = userDao.removeById(id);
            return rowsAffected >= MINIMAL_AFFECTED_ROWS;
        } catch (DaoException e){
            logger.error("Unable to delete user from data source.", e);
            throw new ServiceException("Unable to delete user from data source.", e);
        }
    }
}
