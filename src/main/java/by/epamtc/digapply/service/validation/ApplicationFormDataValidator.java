package by.epamtc.digapply.service.validation;

import java.util.Map;

public interface ApplicationFormDataValidator {

    boolean validate(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificates);

}
