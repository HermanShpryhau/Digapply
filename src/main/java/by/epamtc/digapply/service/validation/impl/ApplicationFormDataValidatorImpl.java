package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;

import java.util.Map;

public class ApplicationFormDataValidatorImpl implements ApplicationFormDataValidator {
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
        return certificateIds != null && !certificateIds.isEmpty();
    }

    private boolean ofEqualSize(Map<String, String> scores, Map<String, String> certificateIds) {
        return scores.size() == certificateIds.size();
    }
}
