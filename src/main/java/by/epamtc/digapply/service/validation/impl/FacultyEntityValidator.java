package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.validation.EntityValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacultyEntityValidator implements EntityValidator<Faculty> {
    private static final String FACULTY_NAME_REGEX = "^[A-Za-z0-9 '.]+$";

    @Override
    public boolean validate(Faculty entity) {
        if (entity.getFacultyId() < 0 || entity.getPlaces() < 0) {
            return false;
        }
        if (entity.getFacultyName() == null ||
                entity.getFacultyShortDescription() == null ||
                entity.getFacultyDescription() == null) {
            return false;
        }
        Pattern namePattern = Pattern.compile(FACULTY_NAME_REGEX);
        Matcher matcher = namePattern.matcher(entity.getFacultyName());
        return matcher.matches();
    }
}
