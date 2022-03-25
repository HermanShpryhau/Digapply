package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.*;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.validation.EntityValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import com.google.common.html.HtmlEscapers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LogManager.getLogger();
    private static final int BEST_FACULTIES_COUNT = 3;
    private static final long MINIMAL_ROWS_AFFECTED = 1L;

    @Autowired
    private FacultyDao facultyDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public Faculty saveFaculty(Faculty faculty, List<Long> subjectIds) throws ServiceException {
        if (!isFacultyEntityValid(faculty)) {
            return null;
        }

        try {
            sanitizeDescription(faculty);
            facultyDao.save(faculty, subjectIds);
            return faculty;
        } catch (DaoException e) {
            logger.error("Unable to save new faculty. {}", e.getMessage());
            throw new ServiceException("Unable to save new faculty.", e);
        }
    }

    @Override
    public List<Faculty> retrieveBestFaculties() throws ServiceException {
        try {
            return facultyDao.findBestFaculties(BEST_FACULTIES_COUNT);
        } catch (DaoException e) {
            logger.error("Unable to retrieve best faculties. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve best faculties.", e);
        }
    }

    @Override
    public List<Faculty> retrieveFacultiesByPage(long page, long elementsPerPage) throws ServiceException {
        try {
            return facultyDao.findAllOnPage(page, elementsPerPage);
        } catch (DaoException e) {
            logger.error("Unable to retrieve faculties by page. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve faculties by page.", e);
        }
    }

    @Override
    public long countPages(long elementsPerPage) throws ServiceException {
        try {
            long rowsCount = facultyDao.getRowsCount();
            long leftover = rowsCount % elementsPerPage == 0 ? 0 : 1;
            return (rowsCount / elementsPerPage) + leftover;
        } catch (DaoException e) {
            logger.error("Unable to retrieve rows count. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve rows count.", e);
        }
    }

    @Override
    public Faculty retrieveFacultyById(long id) throws ServiceException {
        try {
            return facultyDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to retrieve faculty by ID. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve faculty by ID.", e);
        }
    }

    @Override
    public List<Faculty> retrieveAllFaculties() throws ServiceException {
        try {
            return facultyDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to retrieve all faculties. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve all faculties.", e);
        }
    }

    @Override
    public List<Faculty> searchFaculties(String pattern, long page, long elementsPerPage) throws ServiceException {
        try {
            return facultyDao.findByPattern(pattern, page, elementsPerPage);
        } catch (DaoException e){
            logger.error("Unable to search faculties by pattern in name. {}", e.getMessage());
            throw new ServiceException("Unable to search faculties by pattern in name.", e);
        }
    }

    @Override
    public long countPagesForSearchResult(String pattern, long elementsPerPage) throws ServiceException {
        try {
            long rowsCount = facultyDao.getRowsCountForSearch(pattern);
            long leftover = rowsCount % elementsPerPage == 0 ? 0 : 1;
            return (rowsCount / elementsPerPage) + leftover;
        } catch (DaoException e) {
            logger.error("Unable to retrieve count of rows that satisfy search pattern. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve count of rows that satisfy search pattern.", e);
        }
    }

    @Override
    public List<Subject> retrieveSubjectsForFaculty(Faculty faculty) throws ServiceException {
        try {
            return subjectDao.findSubjectsByFaculty(faculty.getId());
        } catch (DaoException e) {
            logger.error("Unable to retrieve subjects by faculty id. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve subjects by faculty id.", e);
        }
    }

    @Override
    public List<Subject> retrieveSubjectsForFaculty(long facultyId) throws ServiceException {
        try {
            return subjectDao.findSubjectsByFaculty(facultyId);
        } catch (DaoException e) {
            logger.error("Unable to retrieve subjects by faculty id. {}", e.getMessage());
            throw new ServiceException("Unable to retrieve subjects by faculty id.", e);
        }
    }

    @Override
    public boolean updateFaculty(Faculty faculty) throws ServiceException {
        if (isFacultyEntityValid(faculty)) {
            try {
                sanitizeDescription(faculty);
                facultyDao.update(faculty);
                return true;
            } catch (DaoException e) {
                logger.error("Unable to update faculty. {}", e.getMessage());
                throw new ServiceException("Unable to update faculty.", e);
            }
        } else {
            return false;
        }
    }

    private void sanitizeDescription(Faculty faculty) {
        String description = faculty.getFacultyDescription();
        description = escapeHtml(description);
        faculty.setFacultyDescription(description);
        String shortDescription = faculty.getFacultyShortDescription();
        shortDescription = escapeHtml(shortDescription);
        faculty.setFacultyShortDescription(shortDescription);
    }

    private String escapeHtml(String content) {
        content = HtmlEscapers.htmlEscaper().escape(content);
        content = content.replace("`", "\\`");
        return content;
    }

    @Override
    public List<ApplicationDto> closeApplication(long facultyId) throws ServiceException {
        Faculty faculty;
        try {
            faculty = facultyDao.findById(facultyId);
            if (faculty == null) {
                return null;
            }
            faculty.setApplicationClosed(true);
            long rowsAffected = facultyDao.update(faculty);
            if (rowsAffected < MINIMAL_ROWS_AFFECTED) {
                return null;
            }
        } catch (DaoException e) {
            logger.error("Unable to fetch faculty by id. {}", e.getMessage());
            throw new ServiceException("Unable to fetch faculty by id.", e);
        }

        try {
            List<Application> facultyApplications = applicationDao.findByFacultyId(facultyId);
            ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
            Map<Application, Integer> scores = new HashMap<>();
            for (Application application : facultyApplications) {
                scores.put(application, applicationService.calculateTotalScore(application.getId()));
            }
            List<Application> acceptedApplications =
                    scores.entrySet().stream()
                            .filter(entry -> entry.getKey().isApproved())
                            .sorted(Map.Entry.<Application, Integer>comparingByValue().reversed())
                            .sorted(Comparator.comparing(e -> e.getKey().getApplyDate()))
                            .limit(faculty.getPlaces())
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

            applicationDao.acceptApplications(acceptedApplications);
            return applicationService.convertToDto(acceptedApplications);
        } catch (DaoException e) {
            logger.error("Unable to accept applications. {}", e.getMessage());
            throw new ServiceException("Unable to accept applications.", e);
        }
    }

    @Override
    public boolean removeFacultyById(long facultyId) throws ServiceException {
        try {
            facultyDao.removeById(facultyId);
            return true;
        } catch (DaoException e) {
            logger.error("Unable to remove faculty by id. {}", e.getMessage());
            throw new ServiceException("Unable to remove faculty by id.", e);
        }
    }

    private boolean isFacultyEntityValid(Faculty faculty) {
        EntityValidator<Faculty> facultyEntityValidator = ValidatorFactory.getInstance().getFacultyEntityValidator();
        return facultyEntityValidator.validate(faculty);
    }
}
