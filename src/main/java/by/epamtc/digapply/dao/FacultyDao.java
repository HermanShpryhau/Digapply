package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Faculty;

import java.util.List;

public interface FacultyDao extends Dao<Faculty> {
    List<Faculty> findBestFaculties(int count) throws DaoException;

    void save(Faculty faculty, List<Long> subjectIds) throws DaoException;
}
