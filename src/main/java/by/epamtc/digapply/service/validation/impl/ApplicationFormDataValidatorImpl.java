package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationFormDataValidatorImpl implements ApplicationFormDataValidator {
    private static final String CERTIFICATE_ID_REGEX = "^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$";
    @Override
    public boolean validate(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) {
        return isIdValid(userId) &&
                isIdValid(facultyId) &&
                areScoresValid(scores) &&
                areCertificateIdsValid(certificateIds) &&
                ofEqualSize(scores, certificateIds);
    }

    @Override
    public boolean validate(Map<String, String> scores, Map<String, String> certificateIds) {
        return areScoresValid(scores) &&
                areCertificateIdsValid(certificateIds) &&
                ofEqualSize(scores, certificateIds);
    }

    private boolean isIdValid(long id) {
        return id > 0;
    }

    private boolean areScoresValid(Map<String, String> scores) {
        return scores != null && !scores.isEmpty();
    }

    private boolean areCertificateIdsValid(Map<String, String> certificateIds) {
        if (certificateIds == null || certificateIds.isEmpty()) {
            return false;
        }
        for (Map.Entry<String, String> certificateId : certificateIds.entrySet()) {
            Pattern certificateIdPattern = Pattern.compile(CERTIFICATE_ID_REGEX);
            Matcher matcher = certificateIdPattern.matcher(certificateId.getValue());
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    private boolean ofEqualSize(Map<String, String> scores, Map<String, String> certificateIds) {
        return scores.size() == certificateIds.size();
    }
}
