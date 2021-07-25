package by.epamtc.digapply.service.validation.impl;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.validation.EntityValidator;

/**
 * Faculty entity validator.
 */
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
