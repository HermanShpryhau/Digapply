package by.epamtc.digapply.validator;

import by.epamtc.digapply.validator.impl.UserDataValidatorImpl;

public class DataValidatorFactory {
    private final UserDataValidator userDataValidator = new UserDataValidatorImpl();

    private DataValidatorFactory() {
    }

    public static DataValidatorFactory getInstance() {
        return Holder.INSTANCE;
    }

    public UserDataValidator getUserDataValidator() {
        return userDataValidator;
    }

    private static class Holder {
        static final DataValidatorFactory INSTANCE = new DataValidatorFactory();
    }
}
