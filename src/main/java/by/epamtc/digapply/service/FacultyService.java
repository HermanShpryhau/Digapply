package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;

import java.util.List;

public interface FacultyService {

    List<Faculty> retrieveBestFaculties() throws ServiceException;

    List<Faculty> retrieveFacultiesByPage(long page, long elementsPerPage) throws ServiceException;

    long countPages(long elementsPerPage) throws ServiceException;

    Faculty retrieveFacultyById(long id) throws ServiceException;

    List<Faculty> searchFaculties(String pattern, long page, long elementsPerPage) throws ServiceException;

    long countPagesForSearchResult(String pattern, long elementsPerPage) throws ServiceException;

    List<Subject> retrieveSubjectsForFaculty(Faculty faculty) throws ServiceException;

    boolean updateFaculty(Faculty faculty) throws ServiceException;

    Faculty addFaculty(Faculty faculty, List<Long> subjectIds) throws ServiceException;

    boolean removeFacultyById(long facultyId) throws ServiceException;
}
