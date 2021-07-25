package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Faculty;

import java.util.List;

/**
 * Faculty entity DAO.
 */
public interface FacultyDao extends Dao<Faculty> {
    /**
     * Fetches faculties with most applications.
     * @param count Number of faculties to fetch.
     * @return List of faculties with most applications.
     */
    List<Faculty> findBestFaculties(int count) throws DaoException;

    /**
     * Saves faculty and adds faculty-subject links to DB.
     * @param faculty Faculty entity.
     * @param subjectIds IDs of required subjects.
     */
    void save(Faculty faculty, List<Long> subjectIds) throws DaoException;

    /**
     * Fetches faculties on certain page with search patter in name.
     * @param pattern Search pattern.
     * @param page Page number.
     * @param elementsPerPage Number of elements per page.
     * @return List of faculties matching search pattern on page.
     */
    List<Faculty> findByPattern(String pattern, long page, long elementsPerPage) throws DaoException;

    /**
     * Gets number of rows of search result.
     * @param pattern Search pattern.
     * @return Number of search results.
     */
    long getRowsCountForSearch(String pattern) throws DaoException;
}
