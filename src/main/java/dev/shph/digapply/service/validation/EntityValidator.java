package dev.shph.digapply.service.validation;

import dev.shph.digapply.entity.Identifiable;

/**
 * Validates entity bean.
 * @param <T> Type of validated entity.
 */
public interface EntityValidator<T extends Identifiable> {

    /**
     * Validates entity object fields.
     * @param entity Entity to validate.
     * @return {@code true} if entity is valid, {@code false} otherwise.
     */
    boolean validate(T entity);

}
