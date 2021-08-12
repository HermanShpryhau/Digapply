package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.SubjectDao;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubjectServiceImpl implements SubjectService {
    private static final Logger logger = LogManager.getLogger();
    private static final long NEW_SUBJECT_ID = 0;
    private static final Pattern subjectNamePattern = Pattern.compile("[A-z0-9\'\\\"\\-\\.,\\s]+");

    @Override
    public List<Subject> retrieveAllSubjects() throws ServiceException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            return subjectDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to retrieve all subjects. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve all subjects.", e);
        }
    }

    @Override
    public Subject retrieveSubjectById(long id) throws ServiceException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            return subjectDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to retrieve subject by id. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve subject by id.", e);
        }
    }

    @Override
    public boolean saveSubject(String subjectName) throws ServiceException {
        if (subjectName == null) {
            return false;
        }
        Matcher subjectNameMatcher = subjectNamePattern.matcher(subjectName);
        if (!subjectNameMatcher.matches()) {
            return false;
        }
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            Subject subject = new Subject(NEW_SUBJECT_ID, subjectName);
            subjectDao.save(subject);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to save subject to data source. {}", e.getMessage());
            throw new ServiceException("Unable to save subject to data source.", e);
        }
    }

    @Override
    public boolean updateSubject(Subject subject) throws ServiceException {
        if (subject == null) {
            return false;
        }
        if (!isSubjectNameValid(subject)) {
            return false;
        }
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            subjectDao.update(subject);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to update subject. {}", e.getMessage());
            throw new ServiceException("Unable to update subject.", e);
        }
    }

    private boolean isSubjectNameValid(Subject subject) {
        Matcher subjectNameMatcher = subjectNamePattern.matcher(subject.getSubjectName());
        return subjectNameMatcher.matches();
    }

    @Override
    public boolean removeSubject(long id) throws ServiceException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        try {
            subjectDao.removeById(id);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to delete subject. {}", e.getMessage());
            throw new ServiceException("Unable to delete subject.", e);
        }
    }
}
