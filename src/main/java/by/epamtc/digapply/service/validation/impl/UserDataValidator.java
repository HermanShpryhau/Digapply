package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.validation.EntityValidator;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidator implements EntityValidator<User> {
    private static final String NAME_REGEX = "^([А-Я][а-яё]+|[A-Z][a-z]+)$";
    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final int MINIMAL_PASSWORD_LENGTH = 8;
    private final Predicate<User> validationPredicate;

    private UserDataValidator(Predicate<User> validationPredicate) {
        this.validationPredicate = validationPredicate;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean validate(User user) {
        if (user == null) {
            return false;
        }
        return validationPredicate.test(user);
    }

    public static class Builder {
        private Predicate<User> validationPredicate = u -> true;

        public Builder() {
        }

        public Builder validateNameAndSurname() {
            validationPredicate = validationPredicate
                    .and(u -> {
                        Pattern namePattern = Pattern.compile(NAME_REGEX);
                        String name = u.getName();
                        if (name == null) {
                            return false;
                        }
                        Matcher matcher = namePattern.matcher(name);
                        return matcher.matches();
                    })
                    .and(u -> {
                        Pattern surnamePatter = Pattern.compile(NAME_REGEX);
                        String surname = u.getSurname();
                        if (surname == null) {
                            return false;
                        }
                        Matcher matcher = surnamePatter.matcher(surname);
                        return matcher.matches();
                    });
            return this;
        }

        public Builder validateEmail() {
            validationPredicate = validationPredicate.and(
                    u -> {
                        Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
                        String email = u.getEmail();
                        if (email == null) {
                            return false;
                        }
                        Matcher matcher = emailPattern.matcher(email);
                        return matcher.matches();
                    });
            return this;
        }

        public Builder validatePassword() {
            validationPredicate = validationPredicate.and(
                    u -> {
                        String password = u.getPassword();
                        if (password == null) {
                            return false;
                        }
                        return password.length() >= MINIMAL_PASSWORD_LENGTH;
                    }
            );
            return this;
        }

        public UserDataValidator build() {
            return new UserDataValidator(validationPredicate);
        }
    }
}
