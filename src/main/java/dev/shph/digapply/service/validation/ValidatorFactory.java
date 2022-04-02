package dev.shph.digapply.service.validation;

import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.service.validation.impl.ApplicationFormDataValidatorImpl;
import dev.shph.digapply.service.validation.impl.FacultyEntityValidator;
import dev.shph.digapply.service.validation.impl.SubjectFormDataValidatorImpl;
import dev.shph.digapply.service.validation.impl.UserEntityValidator;

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
