package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.model.User;
import by.epamtc.digapply.service.validation.EntityValidator;
import org.springframework.stereotype.Component;

@Component
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
