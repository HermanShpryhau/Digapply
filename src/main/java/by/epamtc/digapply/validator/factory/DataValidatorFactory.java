package by.epamtc.digapply.validator.factory;

import by.epamtc.digapply.validator.UserDataValidator;
import by.epamtc.digapply.validator.impl.UserDataValidatorImpl;

public class DataValidatorFactory {
    private final UserDataValidator userDataValidator = new UserDataValidatorImpl();

    private static class Holder {
        static final DataValidatorFactory INSTANCE = new DataValidatorFactory();
    }

    private DataValidatorFactory() {}

    public static DataValidatorFactory getInstance() { return Holder.INSTANCE; }

    public UserDataValidator getUserDataValidator() {
        return userDataValidator;
    }
}
