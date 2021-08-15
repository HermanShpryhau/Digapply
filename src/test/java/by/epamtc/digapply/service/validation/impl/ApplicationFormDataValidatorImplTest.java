package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationFormDataValidatorImplTest {
    private static ApplicationFormDataValidator validator;
    private static Map<String, String> validScores;
    private static Map<String, String> validCertificateIds;
    private static Map<String, String> invalidKeyScores;
    private static Map<String, String> nonNumericScores;
    private static Map<String, String> invalidKeyValueScores;
    private static Map<String, String> invalidKeyCertificateIds;
    private static Map<String, String> invalidValueCertificateIds;
    private static Map<String, String> invalidKeyValueCertificateIds;
    private static Map<String, String> empty;
    private static Map<String, String> threeCertificateIds;


    @BeforeAll
    static void setUp() {
        validator = ValidatorFactory.getInstance().getApplicationFormDataValidator();
        validScores = new HashMap<>();
        validScores.put("sid-10", "10");
        validScores.put("sid-11", "11");

        validCertificateIds = new HashMap<>();
        validCertificateIds.put("cid-10", "AAAA-1111");
        validCertificateIds.put("cid-11", "A2A4-BCD5");

        invalidKeyScores = new HashMap<>();
        invalidKeyScores.put("bid3", "10");
        invalidKeyScores.put("dqpnf", "11");

        nonNumericScores = new HashMap<>();
        nonNumericScores.put("sid-11", "12");
        nonNumericScores.put("sid-10", "123abc");

        invalidKeyValueScores = new HashMap<>();
        invalidKeyValueScores.put("bid3", "10bc");
        invalidKeyValueScores.put("abc", "1,2.3");

        invalidKeyCertificateIds = new HashMap<>();
        invalidKeyCertificateIds.put("abc-123", "A2A4-BWZY");
        invalidKeyCertificateIds.put("cid12", "A1B2-C3br");

        invalidValueCertificateIds = new HashMap<>();
        invalidValueCertificateIds.put("cid-121", "ASONDSAO");
        invalidValueCertificateIds.put("cid-23", "1234567");

        invalidKeyValueCertificateIds = new HashMap<>();
        invalidKeyValueCertificateIds.put("cid12", "q9w7§-c");
        invalidKeyValueCertificateIds.put("asda.^", "1233-?&^!");

        empty = new HashMap<>();

        threeCertificateIds = new HashMap<>();
        threeCertificateIds.put("cid-11", "11");
        threeCertificateIds.put("cid-12", "12");
        threeCertificateIds.put("cid-13", "13");
    }

    @Test
    void validateAllValidDataTest() {
        assertTrue(validator.validate(1, 1, validScores, validCertificateIds));
    }

    @Test
    void validateInvalidScoreKeysTest() {
        assertFalse(validator.validate(1, 1, invalidKeyScores, validCertificateIds));
    }

    @Test
    void validateInvalidScoreValuesTest() {
        assertFalse(validator.validate(1, 1, nonNumericScores, validCertificateIds));
    }

    @Test
    void validateInvalidScoreDataTest() {
        assertFalse(validator.validate(1, 1, invalidKeyValueScores, validCertificateIds));
    }

    @Test
    void validateEmptyScoresTest() {
        assertFalse(validator.validate(1, 1, empty, validCertificateIds));
    }

    @Test
    void validateNullScoresTest() {
        assertFalse(validator.validate(1, 1, null, validCertificateIds));
    }

    @Test
    void validateInvalidCertificateKeysTest() {
        assertFalse(validator.validate(1, 1, validScores, invalidKeyCertificateIds));
    }

    @Test
    void validateInvalidValueCertificatesTest() {
        assertFalse(validator.validate(1, 1, validScores, invalidValueCertificateIds));
    }

    @Test
    void validateInvalidCertificatesTest() {
        assertFalse(validator.validate(1, 1, validScores, invalidKeyValueCertificateIds));
    }

    @Test
    void validateEmptyCertificatesTest() {
        assertFalse(validator.validate(1, 1, validScores, empty));
    }

    @Test
    void validateNullCertificatesTest() {
        assertFalse(validator.validate(1, 1, validScores, null));
    }

    @Test
    void validateMismatchingSizeMapsTest() {
        assertFalse(validator.validate(1, 1, validScores, threeCertificateIds));
    }
}