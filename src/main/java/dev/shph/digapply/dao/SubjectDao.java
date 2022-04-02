package dev.shph.digapply.dao;

import dev.shph.digapply.entity.Subject;

import java.util.List;

public interface SubjectDao extends Dao<Subject> {

    /**
     * Fetches subjects required for certain faculty.
     * @param facultyId ID of faculty.
     * @return List of Subjects for faculty.
     */
    List<Subject> findSubjectsByFaculty(long facultyId) throws DaoException;

}
