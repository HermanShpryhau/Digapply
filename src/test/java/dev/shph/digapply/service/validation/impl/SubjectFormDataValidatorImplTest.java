package dev.shph.digapply.service.validation.impl;

import dev.shph.digapply.service.validation.SubjectFormDataValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectFormDataValidatorImplTest {
    private static final SubjectFormDataValidator validator = new SubjectFormDataValidatorImpl();
    private static final String VALID_LATIN_NAME = "1st. St. John's Faculty";
    private static final String VALID_CYRILLIC_NAME = "1ого Св. Джон'с";
    private static final String INVALID_NAME = "AFP&%^$_!";

    @Test
    void validateValidLatinNameTest() {
        assertTrue(validator.validate(VALID_LATIN_NAME));
    }

    @Test
    void validateValidCyrillicNameTest() {
        assertTrue(validator.validate(VALID_CYRILLIC_NAME));
    }

    @Test
    void validateInvalidNameTest() {
        assertFalse(validator.validate(INVALID_NAME));
    }
}