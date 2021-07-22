package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.User;

public interface UserDao extends Dao<User>{

    User findByEmail(String email) throws DaoException;

}
