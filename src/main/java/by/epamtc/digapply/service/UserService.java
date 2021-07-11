package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.User;

public interface UserService {

    User login(String email, String password) throws ServiceException;

    boolean register(String firstName, String lastName, String email, String password) throws ServiceException;

}
