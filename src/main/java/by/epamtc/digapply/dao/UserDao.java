package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.User;

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
     * @param roleId ID of role to assign to user.
     * @return Number of affected rows.
     */
    long updateUserRole(long userId, long roleId) throws DaoException;

}
