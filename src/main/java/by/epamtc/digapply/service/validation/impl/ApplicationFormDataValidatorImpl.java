package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ApplicationFormDataValidatorImpl implements ApplicationFormDataValidator {
    private static final String CERTIFICATE_ID_REGEX = "^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$";
    private static final String CERTIFICATE_ID_KEY_REGEX = "^cid-(\\d+)$";
    private static final String SCORE_ID_KEY_REGEX = "^sid-(\\d+)$";

    @Override
    public boolean validate(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) {
        return isIdValid(userId) &&
                isIdValid(facultyId) &&
                validateScoreMaps(scores, certificateIds);
    }

    @Override
    public boolean validateScoreMaps(Map<String, String> scores, Map<String, String> certificateIds) {
        return areScoresValid(scores) &&
                areCertificateIdsValid(certificateIds) &&
                ofEqualSize(scores, certificateIds);
    }

    private boolean isIdValid(long id) {
        return id > 0;
    }

    private boolean areScoresValid(Map<String, String> scores) {
        if (scores == null || scores.isEmpty()) {
            return false;
        }
        for (Map.Entry<String, String> score : scores.entrySet()) {
            if (!matchesPattern(SCORE_ID_KEY_REGEX, score.getKey()) || !isScoreStringNumeric(score)) {
                return false;
            }
        }
        return true;
    }

    private boolean isScoreStringNumeric(Map.Entry<String, String> score) {
        try {
            Integer.parseInt(score.getValue());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean areCertificateIdsValid(Map<String, String> certificateIds) {
        if (certificateIds == null || certificateIds.isEmpty()) {
            return false;
        }
        for (Map.Entry<String, String> certificateId : certificateIds.entrySet()) {
            if (!matchesPattern(CERTIFICATE_ID_KEY_REGEX, certificateId.getKey())) return false;
            if (!matchesPattern(CERTIFICATE_ID_REGEX, certificateId.getValue())) return false;
        }
        return true;
    }

    private boolean matchesPattern(String regex, String key) {
        Pattern certificateIdKeyPattern = Pattern.compile(regex);
        Matcher keyMatcher = certificateIdKeyPattern.matcher(key);
        return keyMatcher.matches();
    }

    private boolean ofEqualSize(Map<String, String> scores, Map<String, String> certificateIds) {
        return scores.size() == certificateIds.size();
    }
}
