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
     * Checks if user's role ID is ADMIN role ID.
     * @param userRoleId Role ID to check.
     * @return If given role ID is ADMIN role ID.
     */
    boolean hasAdminRights(long userRoleId);

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

    boolean updateUserData(long userId, String firstName, String lastName) throws ServiceException;

    boolean updatePassword(long userId, String password) throws ServiceException;

    boolean giveAdminRights(long userId) throws ServiceException;

    boolean revokeAdminRights(long userId) throws ServiceException;

    boolean deleteUser(long id) throws ServiceException;

}
