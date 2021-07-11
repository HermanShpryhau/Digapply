package by.epamtc.digapply.validator;

public interface UserDataValidator {
    boolean validateName(String name);

    boolean validatePassword(String password);

    boolean validateEmail(String email);
}
