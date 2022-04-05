package dev.shph.digapply.dao;

import dev.shph.digapply.entity.User;
import dev.shph.digapply.entity.UserRole;

public interface UserDao extends Dao<User>{

    /**
     * Fetches user with given email address.
     * @param email String containing email address.
     * @return User entity or null if there is no user with such email.
     */
    User findByEmail(String email) throws DaoException;

    /**
     * Updates user's password.
     * @param userId ID of user to update.
     * @param password New password value.
     * @return Number of affected rows.
     */
    long updatePassword(long userId, String password) throws DaoException;

    /**
     * Changes user's role id.
     * @param userId ID of user to update.
     * @param role Role to assign to user.
     * @return Number of affected rows.
     */
    long updateUserRole(long userId, UserRole role) throws DaoException;

}
