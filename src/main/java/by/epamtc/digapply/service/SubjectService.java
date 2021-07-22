package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> retrieveAllSubjects() throws ServiceException;
}
