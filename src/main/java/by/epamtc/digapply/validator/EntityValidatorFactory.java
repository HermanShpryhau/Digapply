package by.epamtc.digapply.validator;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.validator.impl.FacultyEntityValidator;
import by.epamtc.digapply.validator.impl.UserEntityValidator;

public class EntityValidatorFactory {
    private final EntityValidator<User> userEntityValidator = new UserEntityValidator();
    private final EntityValidator<Faculty> facultyEntityValidator = new FacultyEntityValidator();

    private EntityValidatorFactory() {
    }

    public static EntityValidatorFactory getInstance() {
        return Holder.INSTANCE;
    }

    public EntityValidator<User> getUserDataValidator() {
        return userEntityValidator;
    }

    public EntityValidator<Faculty> getFacultyEntityValidator() {
        return facultyEntityValidator;
    }

    private static class Holder {
        static final EntityValidatorFactory INSTANCE = new EntityValidatorFactory();
    }
}
