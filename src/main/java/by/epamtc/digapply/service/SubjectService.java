package by.epamtc.digapply.service;

import by.epamtc.digapply.model.Subject;

import java.util.List;

public interface SubjectService {

    /**
     * Retrieves all subject entities.
     * @return List of all subjects.
     */
    List<Subject> retrieveAllSubjects() throws ServiceException;

    /**
     * Retrieves subject entity by ID.
     * @param id ID of subject.
     * @return Subject entity.
     */
    Subject retrieveSubjectById(long id) throws ServiceException;

    /**
     * Adds subject with given name to data source.
     * @param subjectName Name of the subject.
     * @return {@code true} if subject was added successfully, {@code false} otherwise.
     */
    boolean saveSubject(String subjectName) throws ServiceException;

    /**
     * Updates subject data to data in supplied entity.
     * @param subject Subject entity with new data.
     * @return {@code true} if subject was updated successfully, {@code false} otherwise.
     */
    boolean updateSubject(Subject subject) throws ServiceException;

    /**
     * Deletes subject from data source.
     * @param id ID of subject to delete.
     * @return {@code true} if subject was deleted successfully, {@code false} otherwise.
     */
    boolean removeSubject(long id) throws ServiceException;

}
