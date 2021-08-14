package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.entity.UserRole;
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
     * @param role Role  to check.
     * @return If given role ID is ADMIN role ID.
     */
    boolean hasAdminRights(UserRole role);

    /**
     * Register new user in system.
     * @param firstName First name of user.
     * @param lastName Last name of user.
     * @param email Email address.
     * @param password Password (not hashed).
     * @return {@code true} if user is registered, {@code false} otherwise.
     */
    boolean register(String firstName, String lastName, String email, String password) throws ServiceException;

    /**
     * Retrieve full name of user by ID.
     * @param userId User ID.
     * @return Full name of user.
     */
    String getFullNameById(long userId) throws ServiceException;

    /**
     * Retrieve a list of all users as DTO.
     * @return List of users as DTOs.
     */
    List<UserDto> retrieveAllUsersAsDto() throws ServiceException;

    /**
     * Retrieve user entity by ID.
     * @param id ID of user.
     * @return User entity.
     */
    User retrieveUserById(long id) throws ServiceException;

    /**
     * Update user's first and last name.
     * @param userId ID of user.
     * @param firstName New first name.
     * @param lastName New last name.
     * @return {@code true} if user was updated successfully, {@code false} otherwise.
     */
    boolean updateUserData(long userId, String firstName, String lastName) throws ServiceException;

    /**
     * Update user's password
     * @param userId ID of user.
     * @param password Unhashed password.
     * @return {@code true} if user was updated successfully, {@code false} otherwise.
     */
    boolean updatePassword(long userId, String password) throws ServiceException;

    /**
     * Assign admin rights to user.
     * @param userId ID of user.
     * @return {@code true} if role was updated successfully, {@code false} otherwise.
     */
    boolean giveAdminRights(long userId) throws ServiceException;

    /**
     * Revoke admin rights from user.
     * @param userId ID of user.
     * @return {@code true} if role was updated successfully, {@code false} otherwise.
     */
    boolean revokeAdminRights(long userId) throws ServiceException;

    /**
     * Delete user from data source
     * @param id ID of user.
     * @return {@code true} if user was deleted successfully, {@code false} otherwise.
     */
    boolean deleteUser(long id) throws ServiceException;

}
