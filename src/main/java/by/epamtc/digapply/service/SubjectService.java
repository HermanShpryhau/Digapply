package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Subject;

import java.util.List;

/**
 * Business logic connected with Subjects.
 */
public interface SubjectService {

    /**
     * Retrieves all subjects.
     * @return List of all subjects.
     */
    List<Subject> retrieveAllSubjects() throws ServiceException;

}
