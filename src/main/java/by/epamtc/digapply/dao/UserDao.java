package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void addUser(User user) throws DaoException;

    User getUserById(int id) throws DaoException;

    User getUserByEmail(String email) throws DaoException;

    List<User> getAllUsers() throws DaoException;

    void updateUser(int userId, User user) throws DaoException;

    void deleteUser(User user) throws DaoException;
}
