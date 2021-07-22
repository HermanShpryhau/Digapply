package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Subject;

import java.util.List;

public interface SubjectDao extends Dao<Subject> {
    List<Subject> findSubjectsByFaculty(long facultyId) throws DaoException;
}
