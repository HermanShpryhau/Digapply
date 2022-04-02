package by.epamtc.digapply.service;


import by.epamtc.digapply.model.dto.ApplicationDto;
import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.Subject;

import java.util.List;

public interface FacultyService {

    /**
     * Saves faculty and it's required subjects to data source.
     * @param faculty Faculty entity to save.
     * @param subjectIds List of required subject IDs.
     * @return Saved faculty.
     */
    Faculty saveFaculty(Faculty faculty, List<Long> subjectIds);

    /**
     * Retrieves best faculties.
     * @return List of best faculties.
     */
    List<Faculty> retrieveBestFaculties();

    /**
     * Retrieves faculties for page.
     * @param page Number of page.
     * @param size Number of elements per page.
     * @return List of faculties for page.
     */
    List<Faculty> retrieveFacultiesByPage(int page, int size);

    /**
     * Counts pages need to display all faculties.
     * @param elementsPerPage Number of elements per page.
     * @return Number of pages needed to display all faculties.
     */
    long countPages(long elementsPerPage);

    /**
     * Retrieves all faculties.
     * @return List of all faculties in data source.
     */
    List<Faculty> retrieveAllFaculties();

    /**
     * Retrieves faculty by ID.
     * @param id ID of faculty
     * @return Faculty object with given ID.
     */
    Faculty retrieveFacultyById(long id);

    /**
     * Searches for faculties with given pattern in name.
     * @param pattern Search pattern.
     * @param page Page number.
     * @param size Number of elements on page.
     * @return List of faculties matching search criteria.
     */
    List<Faculty> searchFaculties(String pattern, int page, int size);

    /**
     * Counts number of pages needed to display all search results.
     * @param pattern Search pattern.
     * @param elementsPerPage Number of elements on page.
     * @return Number of pages needed to display search results.
     */
    long countPagesForSearchResult(String pattern, long elementsPerPage);

    /**
     * Updates Faculty entity representation in data source.
     * @param faculty Faculty to update.
     * @return {@code true} if entity was successfully updated, {@code false} if submitted data was invalid.
     */
    boolean updateFaculty(Faculty faculty) throws ServiceException;

    List<ApplicationDto> closeApplication(long facultyId);

    /**
     * Deletes given faulty from data source
     * @param facultyId ID of faculty to delete
     * @return {@code true} if faculty was successfully deleted, {@code false} otherwise.
     */
    boolean removeFacultyById(long facultyId);
}
