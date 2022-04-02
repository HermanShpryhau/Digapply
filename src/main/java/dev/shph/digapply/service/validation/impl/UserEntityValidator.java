package dev.shph.digapply.service.validation.impl;

import dev.shph.digapply.entity.User;
import dev.shph.digapply.service.validation.EntityValidator;

public class UserEntityValidator implements EntityValidator<User> {
    @Override
    public boolean validate(User entity) {
        EntityValidator<User> userDataValidator = UserDataValidator.builder()
                .validateNameAndSurname()
                .validateEmail()
                .validatePassword()
                .build();
        return userDataValidator.validate(entity);
    }
}
