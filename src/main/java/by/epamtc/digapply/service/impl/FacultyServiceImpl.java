package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.FacultyDao;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LogManager.getLogger();
    private static final int BEST_FACULTIES_COUNT = 3;

    private final FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();

    @Override
    public List<Faculty> retrieveBestFaculties() throws ServiceException {
        try {
            return facultyDao.findBestFaculties(BEST_FACULTIES_COUNT);
        } catch (DaoException e) {
            logger.error("Unable to retrieve best faculties.", e);
            throw new ServiceException("Unable to retrieve best faculties.", e);
        }
    }

    @Override
    public List<Faculty> retrieveAllFaculties() throws ServiceException {
        try {
            return facultyDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to retrieve best faculties.", e);
            throw new ServiceException("Unable to retrieve best faculties.", e);
        }
    }

    @Override
    public List<Faculty> retrieveFacultiesByPage(long page, long count) throws ServiceException {
        try {
            return facultyDao.findAllOnPage(page, count);
        } catch (DaoException e) {
            logger.error("Unable to retrieve best faculties.", e);
            throw new ServiceException("Unable to retrieve best faculties.", e);
        }
    }
}
