package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.ApplicationDao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.ResultDao;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Result;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationServiceImpl implements ApplicationService {
    private static final Logger logger = LogManager.getLogger();
    private static final String SCORE_PREFIX = "sid-";
    private static final String CERTIFICATE_ID_PREFIX = "cid-";

    @Override
    public boolean hasApplication(long userId) throws ServiceException {
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            return applicationDao.findByUserId(userId) != null;
        } catch (DaoException e) {
            logger.error("Unable to fetch application by user id.", e);
            throw new ServiceException("Unable to fetch application by user id.", e);
        }
    }

    @Override
    public Application retrieveApplicationByUserId(long userId) throws ServiceException {
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            return applicationDao.findByUserId(userId);
        } catch (DaoException e) {
            logger.error("Unable to fetch application by user id.", e);
            throw new ServiceException("Unable to fetch application by user id.", e);
        }
    }

    @Override
    public int calculateTotalScore(long applicationId) throws ServiceException {
        ResultDao resultDao = DaoFactory.getInstance().getResultDao();
        try {
            List<Result> results = resultDao.findByApplicationId(applicationId);
            int total = 0;
            for (Result result : results) {
                total += result.getScore();
            }
            return total;
        } catch (DaoException e) {
            logger.error("Unable to fetch results for application id.", e);
            throw new ServiceException("Unable to fetch results for application id.", e);
        }
    }

    @Override
    public boolean saveApplication(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException {
        ApplicationFormDataValidator validator = ValidatorFactory.getInstance().getApplicationFormDataValidator();
        if (!validator.validate(userId, facultyId, scores, certificateIds)) {
            return false;
        }

        List<Result> results = buildResultsList(scores, certificateIds);
        Application application = buildApplication(userId, facultyId);
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            applicationDao.save(application, results);
        } catch (DaoException e) {
            logger.error("Unable to save application to data source", e);
            throw new ServiceException("Unable to save application to data source", e);
        }

        return true;
    }

    private Application buildApplication(long userId, long facultyId) {
        Application application = new Application();
        application.setUserId(userId);
        application.setFacultyId(facultyId);
        return application;
    }

    private List<Result> buildResultsList(Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException {
        Map<Long, String> scoresBySubject = buildIdBasedMap(scores, SCORE_PREFIX);
        Map<Long, String> certificateIdsBySubject = buildIdBasedMap(certificateIds, CERTIFICATE_ID_PREFIX);
        List<Result> resultList = new ArrayList<>();
        for (Map.Entry<Long, String> score : scoresBySubject.entrySet()) {
            String certificateId = certificateIdsBySubject.get(score.getKey());
            if (certificateId == null) {
                resultList.clear();
                break;
            }
            int scoreValue;
            try {
                scoreValue = Integer.parseInt(score.getValue());
            } catch (NumberFormatException e) {
                logger.error("Invalid score value", e);
                throw new ServiceException("Invalid score value", e);
            }
            Result result = buildResult(score.getKey(), scoreValue, certificateId);
            resultList.add(result);
        }
        return resultList;
    }

    private Result buildResult(long subjectId, int score, String certificateId) {
        Result result = new Result();
        result.setSubjectId(subjectId);
        result.setScore(score);
        result.setCertificateId(certificateId);
        return result;
    }

    private Map<Long, String> buildIdBasedMap(Map<String, String> map, String keyPrefix) throws ServiceException {
        Map<String, String> mapWithoutPrefixes = removePrefixFromKeys(map, keyPrefix);
        Map<Long, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : mapWithoutPrefixes.entrySet()) {
            try {
                long id = Long.parseLong(entry.getKey());
                result.put(id, entry.getValue());
            } catch (NumberFormatException e) {
                logger.error("Invalid id string.", e);
                throw new ServiceException("Invalid id string.", e);
            }
        }
        return result;
    }

    private Map<String, String> removePrefixFromKeys(Map<String, String> map, String prefix) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            key = key.replace(prefix, "");
            result.put(key, entry.getValue());
        }
        return result;
    }
}
