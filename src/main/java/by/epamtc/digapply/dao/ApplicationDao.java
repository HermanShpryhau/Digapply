package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Result;

import java.util.List;

public interface ApplicationDao extends Dao<Application> {

    /**
     * Save application and corresponding results to DB.
     * @param entity New application.
     * @param results List of results.
     * @return ID of saved entity.
     */
    long save(Application entity, List<Result> results) throws DaoException;

    /**
     * Find application of given user.
     * @param userId ID of user.
     * @return Application entity of user.
     */
    Application findByUserId(long userId) throws DaoException;

    /**
     * Find all applications for given faculty.
     * @param facultyId ID of faculty.
     * @return List of applications for the faculty,
     */
    List<Application> findByFacultyId(long facultyId) throws DaoException;

    /**
     * Update results in an applicaiton.
     * @param id ID of application.
     * @param results List of updated results.
     * @return Number of affected rows.
     */
    long update(long id, List<Result> results) throws DaoException;

    /**
     * Add applications to list of accepted applications.
     * @param applications List of applications.
     * @return ID of first accepted application.
     */
    long acceptApplications(List<Application> applications) throws DaoException;

}
