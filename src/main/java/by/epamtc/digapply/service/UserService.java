package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password) throws ServiceException;

}
