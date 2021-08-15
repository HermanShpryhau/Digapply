package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityValidatorTest {
    private static final UserEntityValidator validator = new UserEntityValidator();
    private static final String VALID_LATIN_NAME = "John";
    private static final String VALID_CYRILLIC_NAME = "Иван";
    private static final String INVALID_NAME = "AFP&%^$_!";
    private static final String VALID_EMAIL = "example@mail.com";
    private static final String INVALID_EMAIL = "email";
    private static final String VALID_PASSWORD = "testtest";
    private static final String INVALID_PASSWORD = "test";

    @Test
    void validateValidUserEntityTest() {
        User user = new User();
        user.setUserId(2);
        user.setRoleId(2);
        user.setName(VALID_LATIN_NAME);
        user.setSurname(VALID_CYRILLIC_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        assertTrue(validator.validate(user));
    }

    @Test
    void validateInvalidNameTest() {
        User user = new User();
        user.setUserId(2);
        user.setRoleId(2);
        user.setName(INVALID_NAME);
        user.setSurname(VALID_CYRILLIC_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        assertFalse(validator.validate(user));
    }

    @Test
    void validateInvalidSurnameTest() {
        User user = new User();
        user.setUserId(2);
        user.setRoleId(2);
        user.setName(VALID_LATIN_NAME);
        user.setSurname(INVALID_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        assertFalse(validator.validate(user));
    }

    @Test
    void validateInvalidEmailTest() {
        User user = new User();
        user.setUserId(2);
        user.setRoleId(2);
        user.setName(VALID_LATIN_NAME);
        user.setSurname(VALID_CYRILLIC_NAME);
        user.setEmail(INVALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        assertFalse(validator.validate(user));
    }

    @Test
    void validateInvalidPasswordTest() {
        User user = new User();
        user.setUserId(2);
        user.setRoleId(2);
        user.setName(VALID_LATIN_NAME);
        user.setSurname(VALID_CYRILLIC_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(INVALID_PASSWORD);
        assertFalse(validator.validate(user));
    }

    @Test
    void validateInvalidIdTest() {
        User user = new User();
        user.setUserId(-1);
        user.setRoleId(2);
        user.setName(VALID_LATIN_NAME);
        user.setSurname(VALID_CYRILLIC_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(INVALID_PASSWORD);
        assertFalse(validator.validate(user));
    }

    @Test
    void validateInvalidRoleIdTest() {
        User user = new User();
        user.setUserId(2);
        user.setRoleId(-1);
        user.setName(VALID_LATIN_NAME);
        user.setSurname(VALID_CYRILLIC_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(INVALID_PASSWORD);
        assertFalse(validator.validate(user));
    }
}