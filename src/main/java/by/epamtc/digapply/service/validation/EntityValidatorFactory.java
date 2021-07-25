package by.epamtc.digapply.service.validation;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.validation.impl.FacultyEntityValidator;
import by.epamtc.digapply.service.validation.impl.UserEntityValidator;

/**
 * Thread-safe singleton EntityValidator implementations provider.
 */
public class EntityValidatorFactory {
    private final EntityValidator<User> userEntityValidator = new UserEntityValidator();
    private final EntityValidator<Faculty> facultyEntityValidator = new FacultyEntityValidator();

    private EntityValidatorFactory() {
    }

    /**
     * Gets instance of {@link EntityValidatorFactory}.
     * @return Instance of singleton.
     */
    public static EntityValidatorFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets implementation of {@link EntityValidator}
     * @return {@link EntityValidator} implementation for {@link User} entities.
     */
    public EntityValidator<User> getUserDataValidator() {
        return userEntityValidator;
    }

    /**
     * Gets implementation of {@link EntityValidator}
     * @return {@link EntityValidator} implementation for {@link Faculty} entities.
     */
    public EntityValidator<Faculty> getFacultyEntityValidator() {
        return facultyEntityValidator;
    }

    private static class Holder {
        static final EntityValidatorFactory INSTANCE = new EntityValidatorFactory();
    }
}
