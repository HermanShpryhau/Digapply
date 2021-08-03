package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.*;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Result;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.entity.dto.ResultDto;
import by.epamtc.digapply.service.*;
import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
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
    public List<ApplicationDto> retrieveApplicationsByFaculty(long facultyId) throws ServiceException {
        List<ApplicationDto> dtos = new ArrayList<>();
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            List<Application> applications = applicationDao.findByFacultyId(facultyId);
            for (Application application : applications) {
                dtos.add(buildApplicationDto(application));
            }
            return dtos;
        } catch (DaoException e) {
            logger.error("Unable to fetch applications by faculty id.", e);
            throw new ServiceException("Unable to fetch applications by faculty id.", e);
        }
    }

    @Override
    public List<ApplicationDto> retrieveAllApplicationsDto() throws ServiceException {
        List<ApplicationDto> dtos = new ArrayList<>();

        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            List<Application> applications = applicationDao.findAll();
            for (Application application : applications) {
                dtos.add(buildApplicationDto(application));
            }
        } catch (DaoException e) {
            logger.error("Unable to retrieve all applications", e);
            throw new ServiceException("Unable to retrieve all applications", e);
        }

        return dtos;
    }

    @Override
    public ApplicationDto retrieveApplicationDtoById(long id) throws ServiceException {
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            Application application = applicationDao.findById(id);
            return buildApplicationDto(application);
        } catch (DaoException e) {
            logger.error("Unable to fetch application by id.", e);
            throw new ServiceException("Unable to fetch application by id.", e);
        }
    }

    private ApplicationDto buildApplicationDto(Application application) throws ServiceException {
        ApplicationDto dto = new ApplicationDto();
        dto.setApplicationId(application.getId());
        dto.setApplicantId(application.getUserId());
        dto.setApplicantName(getApplicantNameById(application.getUserId()));
        dto.setFacultyName(getFacultyNameById(application.getFacultyId()));
        List<ResultDto> results = getResultsForApplication(application.getId());
        dto.setResults(results);
        dto.setTotalScore(calculateTotalScoreFromDto(results));
        dto.setApplyDate(application.getApplyDate());
        dto.setApproved(application.isApproved());
        dto.setApproveDate(application.getApproveDate());
        return dto;
    }

    private String getApplicantNameById(long userId) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        return userService.getFullNameById(userId);
    }

    private String getFacultyNameById(long facultyId) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findById(facultyId).getFacultyName();
        } catch (DaoException e) {
            logger.error("Unable to fetch faculty by id.", e);
            throw new ServiceException("Unable to fetch faculty by id.", e);
        }
    }

    private List<ResultDto> getResultsForApplication(long applicationId) throws ServiceException {
        ResultService resultService = ServiceFactory.getInstance().getResultService();
        return resultService.retrieveResultsForApplication(applicationId);
    }

    private int calculateTotalScoreFromDto(List<ResultDto> results) {
        return results.stream().mapToInt(ResultDto::getScore).sum();
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
    public boolean cancelApplication(long userId) throws ServiceException {
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            Application application = applicationDao.findByUserId(userId);
            if (application == null) {
                return false;
            }
            applicationDao.remove(application);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to remove application from DB.", e);
            throw new ServiceException("Unable to remove application from DB.", e);
        }
    }

    @Override
    public boolean approveApplication(long applicationId) throws ServiceException {
        ApplicationDao applicationDao = DaoFactory.getInstance().getApplicationDao();
        try {
            Application application = applicationDao.findById(applicationId);
            if (application == null) {
                return false;
            }
            application.setApproved(true);
            application.setApproveDate(new Timestamp(System.currentTimeMillis()));
            applicationDao.updateEntity(application);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to approve application.", e);
            throw new ServiceException("Unable to approve application.", e);
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
