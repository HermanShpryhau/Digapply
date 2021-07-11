package by.epamtc.digapply.validator.impl;

import by.epamtc.digapply.validator.UserDataValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidatorImpl implements UserDataValidator {
    // Regex to match any name string with special character ex. d'Arres, Bar-Hile, etc.
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z \\-\\.\\']*$");
    // Regex to match password with any characters at least 8 characters in length
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9\\d@$!%*?&#-_]{8,}$");
    // Regex to match email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$");

    @Override
    public boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        Matcher matcher = NAME_PATTERN.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

    @Override
    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
