package dev.shph.digapply.dao;

import dev.shph.digapply.entity.Identifiable;

import java.util.List;

/**
 * Base DAO interface.
 * @param <T>
 */
public interface Dao<T extends Identifiable> {

    /**
     * Saves entity to DB table.
     * @param entity Entity object to save.
     */
    long save(T entity) throws DaoException;

    /**
     * Fetches entity object from DB by ID.
     * @param id ID of entity to find.
     * @return Entity object from DB.
     */
    T findById(long id) throws DaoException;

    /**
     * Fetches all entities from table;
     * @return List of all entities in the table;
     */
    List<T> findAll() throws DaoException;

    /**
     * Fetches number of entities on given page.
     * @param page Number of page.
     * @param count Number of elements per page.
     * @return List of entities for given page.
     */
    List<T> findAllOnPage(long page, long count) throws DaoException;

    /**
     * Counts rows in table.
     * @return Number of rows in table.
     */
    long getRowsCount() throws DaoException;

    /**
     * Updates entity data in table.
     * @param entity Entity to update. Object must contain updated data.
     */
    long update(T entity) throws DaoException;

    /**
     * Deletes entity from table.
     * @param id id of entity to delete.
     */
    long removeById(long id) throws DaoException;

    /**
     * Deletes given entity from table.
     * @param entity Entity to delete.
     */
    long remove(T entity) throws DaoException;

}
