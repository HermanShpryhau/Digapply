package by.epamtc.digapply.service.validation;

import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.User;
import by.epamtc.digapply.service.validation.impl.ApplicationFormDataValidatorImpl;
import by.epamtc.digapply.service.validation.impl.FacultyEntityValidator;
import by.epamtc.digapply.service.validation.impl.SubjectFormDataValidatorImpl;
import by.epamtc.digapply.service.validation.impl.UserEntityValidator;

public class ValidatorFactory {
    private final EntityValidator<User> userEntityValidator = new UserEntityValidator();
    private final EntityValidator<Faculty> facultyEntityValidator = new FacultyEntityValidator();
    private final ApplicationFormDataValidator applicationFormDataValidator = new ApplicationFormDataValidatorImpl();
    private final SubjectFormDataValidator subjectFormDataValidator = new SubjectFormDataValidatorImpl();

    private ValidatorFactory() {
    }

    public static ValidatorFactory getInstance() {
        return Holder.INSTANCE;
    }

    public EntityValidator<User> getUserDataValidator() {
        return userEntityValidator;
    }

    public EntityValidator<Faculty> getFacultyEntityValidator() {
        return facultyEntityValidator;
    }

    public ApplicationFormDataValidator getApplicationFormDataValidator() {
        return applicationFormDataValidator;
    }

    public SubjectFormDataValidator getSubjectFormDataValidator() {
        return subjectFormDataValidator;
    }

    private static class Holder {
        static final ValidatorFactory INSTANCE = new ValidatorFactory();
    }
}
