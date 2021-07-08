package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Identifiable;

import java.util.List;

public interface Dao<T extends Identifiable> {
    void add(T entity) throws DaoException;

    T findById(long id) throws DaoException;

    List<T> findAll() throws DaoException;

    List<T> findAllOnPage(long page, long count) throws DaoException;

    void updateEntity(T entity) throws DaoException;

    void removeById(long id) throws DaoException;

    void remove(T entity) throws DaoException;
}
