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

public class SubjectServiceImpl implements SubjectService {
    private static final Logger logger = LogManager.getLogger();

    private final SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();

    @Override
    public List<Subject> retrieveAllSubjects() throws ServiceException {
        try {
            return subjectDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to retrieve all subjects", e);
            throw new ServiceException("Unable to retrieve all subjects", e);
        }
    }
}
