package by.epamtc.digapply.validator.impl;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.validator.EntityValidator;

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
