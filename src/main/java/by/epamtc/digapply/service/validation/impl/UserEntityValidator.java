package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.validation.EntityValidator;

/**
 * User entity validator.
 */
public class UserEntityValidator implements EntityValidator<User> {
    @Override
    public boolean validate(User entity) {
        if (entity.getId() < 0 || entity.getRoleId() < 1) {
            return false;
        }
        return entity.getName() != null &&
               entity.getSurname() != null &&
               entity.getEmail() != null &&
               entity.getPassword() != null;
    }
}
