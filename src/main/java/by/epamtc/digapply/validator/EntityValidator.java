package by.epamtc.digapply.validator;

import by.epamtc.digapply.entity.Identifiable;

public interface EntityValidator<T extends Identifiable> {
    boolean validate(T entity);
}
