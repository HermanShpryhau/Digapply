package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.validation.EntityValidator;

public class UserEntityValidator implements EntityValidator<User> {
    @Override
    public boolean validate(User entity) {
        if (entity.getId() < 0 || entity.getRoleId() < 1) {
            return false;
        }
        EntityValidator<User> userDataValidator = UserDataValidator.builder()
                .validateNameAndSurname()
                .validateEmail()
                .validatePassword()
                .build();
        return userDataValidator.validate(entity);
    }
}
