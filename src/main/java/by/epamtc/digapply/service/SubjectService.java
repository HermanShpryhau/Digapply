package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Subject;

import java.util.List;

public interface SubjectService {

    /**
     * Retrieves all subjects.
     * @return List of all subjects.
     */
    List<Subject> retrieveAllSubjects() throws ServiceException;

    Subject retrieveSubjectById(long id) throws ServiceException;

    boolean saveSubject(String subjectName) throws ServiceException;

    boolean updateSubject(Subject subject) throws ServiceException;

    boolean removeSubject(long id) throws ServiceException;

}
