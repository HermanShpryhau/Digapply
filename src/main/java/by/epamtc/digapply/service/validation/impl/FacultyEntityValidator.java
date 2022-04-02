package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.service.validation.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FacultyEntityValidator implements EntityValidator<Faculty> {
    private static final String FACULTY_NAME_REGEX = "^[A-Za-zА-Яа-яёЁ0-9 '.]+$";

    @Override
    public boolean validate(Faculty entity) {
        if (entity.getId() < 0 || entity.getPlaces() < 0) {
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
