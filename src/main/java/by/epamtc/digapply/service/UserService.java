package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.entity.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Authenticates user.
     * @param email Email address.
     * @param password Password (not hashed).
     * @return {@code true} if user is authenticated, {@code false} otherwise.
     */
    User login(String email, String password) throws ServiceException;

    /**
     * Register new user in system.
     * @param firstName First name of user.
     * @param lastName Last name of user.
     * @param email Email address.
     * @param password Password (not hashed).
     * @return {@code true} if user is registered, {@code false} otherwise.
     */
    boolean register(String firstName, String lastName, String email, String password) throws ServiceException;

    String getFullNameById(long userId) throws ServiceException;

    List<UserDto> retrieveAllUsersAsDto() throws ServiceException;

    User retrieveUserById(long id) throws ServiceException;

    boolean updateUserData(User user) throws ServiceException;

    boolean giveAdminRights(long userId) throws ServiceException;

    boolean deleteUser(long id) throws ServiceException;

}
