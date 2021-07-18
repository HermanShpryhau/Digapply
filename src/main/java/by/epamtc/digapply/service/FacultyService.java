package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> retrieveBestFaculties() throws ServiceException;

    List<Faculty> retrieveAllFaculties() throws ServiceException;

    List<Faculty> retrieveFacultiesByPage(long page, long count) throws ServiceException;

    Faculty retrieveFacultyById(long id) throws ServiceException;
}
