package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.User;

/**
 * User entity DAO.
 */
public interface UserDao extends Dao<User>{

    /**
     * Fetches user with given email address.
     * @param email String containing email address.
     * @return User entity or null if there is no user with such email.
     */
    User findByEmail(String email) throws DaoException;

}
