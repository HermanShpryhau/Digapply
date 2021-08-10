package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.FacultyDao;
import by.epamtc.digapply.dao.SubjectDao;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.validation.EntityValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LogManager.getLogger();
    private static final int BEST_FACULTIES_COUNT = 3;

    @Override
    public List<Faculty> retrieveBestFaculties() throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findBestFaculties(BEST_FACULTIES_COUNT);
        } catch (DaoException e) {
            logger.error("Unable to retrieve best faculties.", e);
            throw new ServiceException("Unable to retrieve best faculties.", e);
        }
    }

    @Override
    public List<Faculty> retrieveFacultiesByPage(long page, long elementsPerPage) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findAllOnPage(page, elementsPerPage);
        } catch (DaoException e) {
            logger.error("Unable to retrieve faculties by page.", e);
            throw new ServiceException("Unable to retrieve faculties by page.", e);
        }
    }

    @Override
    public long countPages(long elementsPerPage) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            long rowsCount = facultyDao.getRowsCount();
            long leftover = rowsCount % elementsPerPage == 0 ? 0 : 1;
            return (rowsCount / elementsPerPage) + leftover;
        } catch (DaoException e) {
            logger.error("Unable to retrieve rows count.", e);
            throw new ServiceException("Unable to retrieve rows count.", e);
        }
    }

    @Override
    public Faculty retrieveFacultyById(long id) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to retrieve faculty by ID.", e);
            throw new ServiceException("Unable to retrieve faculty by ID.", e);
        }
    }

    @Override
    public List<Faculty> retrieveAllFaculties() throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to retrieve all faculties.", e);
            throw new ServiceException("Unable to retrieve all faculties.", e);
        }
    }

    @Override
    public List<Faculty> searchFaculties(String pattern, long page, long elementsPerPage) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findByPattern(pattern, page, elementsPerPage);
        } catch (DaoException e){
            logger.error("Unable to search faculties by pattern in name", e);
            throw new ServiceException("Unable to search faculties by pattern in name", e);
        }
    }

    @Override
    public long countPagesForSearchResult(String pattern, long elementsPerPage) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            long rowsCount = facultyDao.getRowsCountForSearch(pattern);
            long leftover = rowsCount % elementsPerPage == 0 ? 0 : 1;
            return (rowsCount / elementsPerPage) + leftover;
        } catch (DaoException e) {
            logger.error("Unable to retrieve count of rows that satisfy search pattern", e);
            throw new ServiceException("Unable to retrieve count of rows that satisfy search pattern", e);
        }
    }

    @Override
    public List<Subject> retrieveSubjectsForFaculty(Faculty faculty) throws ServiceException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            return subjectDao.findSubjectsByFaculty(faculty.getId());
        } catch (DaoException e) {
            logger.error("Unable to retrieve subjects by faculty id", e);
            throw new ServiceException("Unable to retrieve subjects by faculty id", e);
        }
    }

    @Override
    public List<Subject> retrieveSubjectsForFaculty(long facultyId) throws ServiceException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            return subjectDao.findSubjectsByFaculty(facultyId);
        } catch (DaoException e) {
            logger.error("Unable to retrieve subjects by faculty id", e);
            throw new ServiceException("Unable to retrieve subjects by faculty id", e);
        }
    }

    @Override
    public boolean updateFaculty(Faculty faculty) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        if (isFacultyEntityValid(faculty)) {
            try {
                sanitizeDescription(faculty);
                facultyDao.update(faculty);
                return true;
            } catch (DaoException e) {
                logger.error("Unable to update faculty.", e);
                throw new ServiceException("Unable to update faculty.", e);
            }
        } else {
            return false;
        }
    }

    @Override
    public Faculty saveFaculty(Faculty faculty, List<Long> subjectIds) throws ServiceException {
        if (!isFacultyEntityValid(faculty)) {
            return null;
        }

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            sanitizeDescription(faculty);
            facultyDao.save(faculty, subjectIds);
            return faculty;
        } catch (DaoException e) {
            logger.error("Unable to save new faculty", e);
            throw new ServiceException("Unable to save new faculty", e);
        }
    }

    private void sanitizeDescription(Faculty faculty) {
        String description = faculty.getFacultyDescription();
        description = StringEscapeUtils.escapeEcmaScript(description);
        description = StringEscapeUtils.escapeHtml4(description);
        faculty.setFacultyDescription(description);
    }

    @Override
    public boolean removeFacultyById(long facultyId) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            facultyDao.removeById(facultyId);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to remove faculty by id.", e);
            throw new ServiceException("Unable to remove faculty by id.", e);
        }
    }

    private boolean isFacultyEntityValid(Faculty faculty) {
        EntityValidator<Faculty> facultyEntityValidator = ValidatorFactory.getInstance().getFacultyEntityValidator();
        return facultyEntityValidator.validate(faculty);
    }
}
