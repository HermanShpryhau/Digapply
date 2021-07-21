package by.epamtc.digapply.validator.impl;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.validator.EntityValidator;

public class FacultyEntityValidator implements EntityValidator<Faculty> {
    @Override
    public boolean validate(Faculty entity) {
        if (entity.getFacultyId() < 0 || entity.getPlaces() < 0) {
            return false;
        }
        return entity.getFacultyName() != null &&
               entity.getFacultyShortDescription() != null &&
               entity.getFacultyDescription() != null;
    }
}
