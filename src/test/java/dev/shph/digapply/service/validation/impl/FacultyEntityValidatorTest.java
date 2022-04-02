package dev.shph.digapply.service.validation.impl;

import dev.shph.digapply.entity.Faculty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacultyEntityValidatorTest {
    private static final FacultyEntityValidator validator = new FacultyEntityValidator();
    private static final String VALID_LATIN_NAME = "1st. St. John's Faculty";
    private static final String VALID_CYRILLIC_NAME = "1ого Св. Джон'с";
    private static final String INVALID_NAME = "AFP&%^$_!";
    private static final long VALID_ID = 23L;
    private static final long INVALID_ID = -23L;
    private static final int VALID_PLACES_COUNT = 23;
    private static final int INVALID_PLACES_COUNT = -23;
    private static final String TEST_DESCRIPTION = "test";

    @Test
    void validateValidLatinNameTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(VALID_ID);
        faculty.setFacultyName(VALID_LATIN_NAME);
        faculty.setPlaces(VALID_PLACES_COUNT);
        faculty.setFacultyDescription(TEST_DESCRIPTION);
        faculty.setFacultyShortDescription(TEST_DESCRIPTION);
        assertTrue(validator.validate(faculty));
    }

    @Test
    void validateValidCyrillicNameTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(VALID_ID);
        faculty.setFacultyName(VALID_CYRILLIC_NAME);
        faculty.setPlaces(VALID_PLACES_COUNT);
        faculty.setFacultyDescription(TEST_DESCRIPTION);
        faculty.setFacultyShortDescription(TEST_DESCRIPTION);
        assertTrue(validator.validate(faculty));
    }

    @Test
    void validateInvalidIdTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(INVALID_ID);
        faculty.setFacultyName(VALID_CYRILLIC_NAME);
        faculty.setPlaces(INVALID_PLACES_COUNT);
        faculty.setFacultyDescription(TEST_DESCRIPTION);
        faculty.setFacultyShortDescription(TEST_DESCRIPTION);
        assertFalse(validator.validate(faculty));
    }

    @Test
    void validateInvalidPlacesCountTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(VALID_ID);
        faculty.setFacultyName(VALID_CYRILLIC_NAME);
        faculty.setPlaces(INVALID_PLACES_COUNT);
        faculty.setFacultyDescription(TEST_DESCRIPTION);
        faculty.setFacultyShortDescription(TEST_DESCRIPTION);
        assertFalse(validator.validate(faculty));
    }

    @Test
    void validateInvalidNameTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(VALID_ID);
        faculty.setFacultyName(INVALID_NAME);
        faculty.setPlaces(VALID_PLACES_COUNT);
        faculty.setFacultyDescription(TEST_DESCRIPTION);
        faculty.setFacultyShortDescription(TEST_DESCRIPTION);
        assertFalse(validator.validate(faculty));
    }

    @Test
    void validateNoDescriptionTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(VALID_ID);
        faculty.setFacultyName(INVALID_NAME);
        faculty.setPlaces(VALID_PLACES_COUNT);
        faculty.setFacultyShortDescription(TEST_DESCRIPTION);
        assertFalse(validator.validate(faculty));
    }

    @Test
    void validateNoShortDescriptionTest() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(VALID_ID);
        faculty.setFacultyName(INVALID_NAME);
        faculty.setPlaces(VALID_PLACES_COUNT);
        faculty.setFacultyDescription(TEST_DESCRIPTION);
        assertFalse(validator.validate(faculty));
    }
}