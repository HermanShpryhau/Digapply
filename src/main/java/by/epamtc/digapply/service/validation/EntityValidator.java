package by.epamtc.digapply.service.validation;

import by.epamtc.digapply.model.DataBean;

/**
 * Validates entity bean.
 * @param <T> Type of validated entity.
 */
public interface EntityValidator<T extends DataBean> {

    /**
     * Validates entity object fields.
     * @param entity Entity to validate.
     * @return {@code true} if entity is valid, {@code false} otherwise.
     */
    boolean validate(T entity);

}
