package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.dto.ApplicationDto;

import java.util.List;
import java.util.Map;

public interface ApplicationService {

    boolean saveApplication(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException;

    boolean hasApplication(long userId) throws ServiceException;

    List<ApplicationDto> retrieveAllApplicationsDto() throws ServiceException;

    Application retrieveApplicationByUserId(long userId) throws ServiceException;

    List<ApplicationDto> retrieveApplicationsByFaculty(long facultyId) throws ServiceException;

    ApplicationDto retrieveApplicationDtoById(long id) throws ServiceException;

    int calculateTotalScore(long applicationId) throws ServiceException;

    boolean cancelApplication(long userId) throws ServiceException;

    boolean approveApplication(long applicationId) throws ServiceException;

    boolean updateApplication(long applicationId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException;

}
