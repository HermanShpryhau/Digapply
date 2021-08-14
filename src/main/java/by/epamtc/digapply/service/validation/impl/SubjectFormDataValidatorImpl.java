package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.service.validation.SubjectFormDataValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubjectFormDataValidatorImpl implements SubjectFormDataValidator {
    private static final String SUBJECT_NAME_REGEX = "^[A-Za-zА-Яа-яёЁ0-9 '.]+$";

    @Override
    public boolean validate(String subjectName) {
        Pattern namePattern = Pattern.compile(SUBJECT_NAME_REGEX);
        Matcher matcher = namePattern.matcher(subjectName);
        return matcher.matches();
    }
}
