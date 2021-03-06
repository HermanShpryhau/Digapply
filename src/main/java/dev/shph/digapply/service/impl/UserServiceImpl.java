package dev.shph.digapply.service.impl;

import dev.shph.digapply.entity.Application;
import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.entity.dto.UserDto;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.UserService;
import dev.shph.digapply.service.validation.EntityValidator;
import dev.shph.digapply.service.validation.impl.UserDataValidator;
import dev.shph.digapply.service.validation.ValidatorFactory;
import dev.shph.digapply.dao.ApplicationDao;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final int MINIMAL_AFFECTED_ROWS = 1;

    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplicationDao applicationDao;

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
            logger.error("Unable to retrieve user from DB. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve user from DB.", e);
        }

        return user;
    }

    private User retrieveUserByEmail(String email) throws DaoException {
        return userDao.findByEmail(email);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.getPassword().equals(DigestUtils.sha256Hex(password));
    }

    @Override
    public boolean hasAdminRights(UserRole role) {
        return role == UserRole.ADMIN;
    }

    @Override
    public boolean register(String firstName, String lastName, String email, String password) throws ServiceException {
        User user = buildUser(firstName, lastName, email, password);
        if (isUserEntityValid(user)) {
            try {
                userDao.save(user);
            } catch (DaoException e) {
                logger.error("Unable to save new user to Data Source. {}", e.getMessage());
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
        user.setRole(UserRole.USER);
        return user;
    }

    private boolean isUserEntityValid(User user) {
        EntityValidator<User> userEntityValidator = ValidatorFactory.getInstance().getUserDataValidator();
        return userEntityValidator.validate(user);
    }

    @Override
    public String getFullNameById(long userId) throws ServiceException {
        try {
            User user = userDao.findById(userId);
            if (user == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder(user.getName())
                    .append(' ')
                    .append(user.getSurname());
            return sb.toString();
        } catch (DaoException e) {
            logger.error("Unable to fetch user by id. {}", e.getMessage());
            throw new ServiceException("Unable to fetch user by id.", e);
        }
    }

    @Override
    public List<UserDto> retrieveAllUsersAsDto() throws ServiceException {
        List<UserDto> dtos = new ArrayList<>();
        try {
            List<User> users = userDao.findAll();
            for (User user : users) {
                dtos.add(buildUserDto(user));
            }
        } catch (DaoException e) {
            logger.error("Unable to retrieve all users. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve all users.", e);
        }
        return dtos;
    }

    private UserDto buildUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setRoleId(user.getRoleId());
        dto.setRole(user.getRole().name());
        return dto;
    }

    @Override
    public User retrieveUserById(long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to fetch user by id. {}", e.getMessage());
            throw new ServiceException("Unable to fetch user by id.", e);
        }
    }

    @Override
    public boolean updateUserData(long userId, String firstName, String lastName) throws ServiceException {
        User user = new User();
        user.setUserId(userId);
        user.setName(firstName);
        user.setSurname(lastName);
        EntityValidator<User> userDataValidator = UserDataValidator.builder().validateNameAndSurname().build();
        if (userDataValidator.validate(user)) {
            try {
                long affectedRows = userDao.update(user);
                return affectedRows >= MINIMAL_AFFECTED_ROWS;
            } catch (DaoException e) {
                logger.error("Unable to update user data. {}", e.getMessage());
                throw new ServiceException("Unable to update user data.", e);
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(long userId, String password) throws ServiceException {
        EntityValidator<User> userDataValidator = UserDataValidator.builder().validatePassword().build();
        User user = new User();
        user.setPassword(password);
        if (userDataValidator.validate(user)) {
            try {
                long affectedRows = userDao.updatePassword(userId, DigestUtils.sha256Hex(password));
                return affectedRows >= MINIMAL_AFFECTED_ROWS;
            } catch (DaoException e) {
                logger.error("Unable to update user's password. {}", e.getMessage());
                throw new ServiceException("Unable to update user's password.", e);
            }
        }
        return false;
    }

    @Override
    public boolean giveAdminRights(long userId) throws ServiceException {
        try {
            return userDao.updateUserRole(userId, UserRole.ADMIN) >= MINIMAL_AFFECTED_ROWS;
        } catch (DaoException e) {
            logger.error("Unable to update user role. {}", e.getMessage());
            throw new ServiceException("Unable to update user role.", e);
        }
    }

    @Override
    public boolean revokeAdminRights(long userId) throws ServiceException {
        try {
            return userDao.updateUserRole(userId, UserRole.USER) >= MINIMAL_AFFECTED_ROWS;
        } catch (DaoException e) {
            logger.error("Unable to update user role. {}", e.getMessage());
            throw new ServiceException("Unable to update user role.", e);
        }
    }

    @Override
    public boolean deleteUser(long id) throws ServiceException {
        try {
            Application application = applicationDao.findByUserId(id);
            if (application != null) {
                applicationDao.remove(application);
            }
            long rowsAffected = userDao.removeById(id);
            return rowsAffected >= MINIMAL_AFFECTED_ROWS;
        } catch (DaoException e){
            logger.error("Unable to delete user from data source. {}", e.getMessage());
            throw new ServiceException("Unable to delete user from data source.", e);
        }
    }
}
