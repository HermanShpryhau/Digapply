package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.dto.ApplicationDto;

import java.util.List;
import java.util.Map;

public interface ApplicationService {

    /**
     * Covert list of application entities to application DTOs.
     * @param applications List of application entities.
     * @return List application DTOs.
     */
    List<ApplicationDto> convertToDto(List<Application> applications) throws ServiceException;

    /**
     * Save application and results to data source
     * @param userId ID of applicant user.
     * @param facultyId ID of faculty.
     * @param scores Map containing IDs of subjects and relevant scores.
     * @param certificateIds Map containing IDs of subjects and relevant certificate IDs.
     * @return {@code true} if application was added successfully, {@code false} otherwise.
     */
    boolean saveApplication(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException;

    /**
     * Check if given user has already applied.
     * @param userId ID of user.
     * @return {@code true} if user has already applied, {@code false} otherwise.
     */
    boolean hasApplication(long userId) throws ServiceException;

    /**
     * Retrieve all application entities as DTOs.
     * @return List of application DTOs.
     */
    List<ApplicationDto> retrieveAllApplicationsDto() throws ServiceException;

    /**
     * Retrieve application by user ID.
     * @param userId ID of user.
     * @return Application of given user.
     */
    Application retrieveApplicationByUserId(long userId) throws ServiceException;

    /**
     * Retrieve all applications for given faculty.
     * @param facultyId ID of faculty.
     * @return List of applications for faculty.
     */
    List<ApplicationDto> retrieveApplicationsByFaculty(long facultyId) throws ServiceException;

    /**
     * Retrieve application as DTO by ID.
     * @param id ID of application.
     * @return Application DTO with given ID.
     */
    ApplicationDto retrieveApplicationDtoById(long id) throws ServiceException;

    /**
     * Calculate sum of all results for application.
     * @param applicationId ID of applicaiton.
     * @return Sum of result scores.
     */
    int calculateTotalScore(long applicationId) throws ServiceException;

    /**
     * Cancel application of user.
     * @param userId ID of user.
     * @return {@code true} if application was canceled successfully, {@code false} otherwise.
     */
    boolean cancelApplication(long userId) throws ServiceException;

    /**
     * Makes application with given ID approved.
     * @param applicationId ID of application.
     * @return {@code true} if application was updated successfully, {@code false} otherwise.
     */
    boolean approveApplication(long applicationId) throws ServiceException;

    /**
     * Updates results of a given application.
     * @param applicationId ID of application.
     * @param scores Map containing IDs of subjects and relevant scores.
     * @param certificateIds Map containing IDs of subjects and relevant certificate IDs.
     * @return {@code true} if application was updated successfully, {@code false} otherwise.
     */
    boolean updateApplication(long applicationId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException;

}
