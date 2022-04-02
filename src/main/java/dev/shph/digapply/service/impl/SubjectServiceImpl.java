package dev.shph.digapply.service.impl;

import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.SubjectDao;
import dev.shph.digapply.entity.Subject;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.SubjectService;
import dev.shph.digapply.service.validation.SubjectFormDataValidator;
import dev.shph.digapply.service.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private static final Logger logger = LogManager.getLogger();
    private static final long NEW_SUBJECT_ID = 0;
    private static final long MINIMAL_AFFECTED_ROWS = 1L;

    @Autowired
    private SubjectDao subjectDao;

    @Override
    public List<Subject> retrieveAllSubjects() throws ServiceException {
        try {
            return subjectDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to retrieve all subjects. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve all subjects.", e);
        }
    }

    @Override
    public Subject retrieveSubjectById(long id) throws ServiceException {
        try {
            return subjectDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to retrieve subject by id. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve subject by id.", e);
        }
    }

    @Override
    public boolean saveSubject(String subjectName) throws ServiceException {
        SubjectFormDataValidator validator = ValidatorFactory.getInstance().getSubjectFormDataValidator();
        if (subjectName == null || !validator.validate(subjectName)) {
            return false;
        }

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
        SubjectFormDataValidator validator = ValidatorFactory.getInstance().getSubjectFormDataValidator();
        if (subject == null || !validator.validate(subject.getSubjectName())) {
            return false;
        }

        try {
            subjectDao.update(subject);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to update subject. {}", e.getMessage());
            throw new ServiceException("Unable to update subject.", e);
        }
    }

    @Override
    public boolean removeSubject(long id) throws ServiceException {
        try {
            long affectedRows = subjectDao.removeById(id);
            return affectedRows >= MINIMAL_AFFECTED_ROWS;
        } catch (DaoException e) {
            logger.error("Unable to delete subject. {}", e.getMessage());
            throw new ServiceException("Unable to delete subject.", e);
        }
    }
}
