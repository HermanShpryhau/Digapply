package by.epamtc.digapply.service.validation;

import by.epamtc.digapply.entity.Identifiable;

public interface EntityValidator<T extends Identifiable> {
    boolean validate(T entity);
}
