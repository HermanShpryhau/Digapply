package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.User;

/**
 * Business logic connected with Subjects.
 */
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

}
