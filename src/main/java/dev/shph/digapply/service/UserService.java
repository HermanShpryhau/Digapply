package dev.shph.digapply.service;

import dev.shph.digapply.entity.User;
import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.entity.dto.UserDto;

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
     * Registers new user in system.
     * @param firstName First name of user.
     * @param lastName Last name of user.
     * @param email Email address.
     * @param password Password (not hashed).
     * @return {@code true} if user is registered, {@code false} otherwise.
     */
    boolean register(String firstName, String lastName, String email, String password) throws ServiceException;

    /**
     * Retrieves full name of user by ID.
     * @param userId User ID.
     * @return Full name of user.
     */
    String getFullNameById(long userId) throws ServiceException;

    /**
     * Retrieves a list of all users as DTO.
     * @return List of users as DTOs.
     */
    List<UserDto> retrieveAllUsersAsDto() throws ServiceException;

    /**
     * Retrieves user entity by ID.
     * @param id ID of user.
     * @return User entity.
     */
    User retrieveUserById(long id) throws ServiceException;

    /**
     * Updates user's first and last name.
     * @param userId ID of user.
     * @param firstName New first name.
     * @param lastName New last name.
     * @return {@code true} if user was updated successfully, {@code false} otherwise.
     */
    boolean updateUserData(long userId, String firstName, String lastName) throws ServiceException;

    /**
     * Updates user's password
     * @param userId ID of user.
     * @param password Unhashed password.
     * @return {@code true} if user was updated successfully, {@code false} otherwise.
     */
    boolean updatePassword(long userId, String password) throws ServiceException;

    /**
     * Assigns admin rights to user.
     * @param userId ID of user.
     * @return {@code true} if role was updated successfully, {@code false} otherwise.
     */
    boolean giveAdminRights(long userId) throws ServiceException;

    /**
     * Revokes admin rights from user.
     * @param userId ID of user.
     * @return {@code true} if role was updated successfully, {@code false} otherwise.
     */
    boolean revokeAdminRights(long userId) throws ServiceException;

    /**
     * Deletes user from data source
     * @param id ID of user.
     * @return {@code true} if user was deleted successfully, {@code false} otherwise.
     */
    boolean deleteUser(long id) throws ServiceException;

}
