package dev.shph.digapply.dao;

import dev.shph.digapply.entity.Application;
import dev.shph.digapply.entity.Result;

import java.util.List;

public interface ApplicationDao extends Dao<Application> {

    /**
     * Saves application and corresponding results to DB.
     * @param entity New application.
     * @param results List of results.
     * @return ID of saved entity.
     */
    long save(Application entity, List<Result> results) throws DaoException;

    /**
     * Finds application of given user.
     * @param userId ID of user.
     * @return Application entity of user.
     */
    Application findByUserId(long userId) throws DaoException;

    /**
     * Finds all applications for given faculty.
     * @param facultyId ID of faculty.
     * @return List of applications for the faculty,
     */
    List<Application> findByFacultyId(long facultyId) throws DaoException;

    /**
     * Finds all accepted applications for given faculty.
     * @param facultyId ID of faculty.
     * @return List of accepted applications for the faculty,
     */
    List<Application> findAcceptedByFacultyId(long facultyId) throws DaoException;

    /**
     * Updates results in an applicaiton.
     * @param id ID of application.
     * @param results List of updated results.
     * @return Number of affected rows.
     */
    long update(long id, List<Result> results) throws DaoException;

    /**
     * Adds applications to list of accepted applications.
     * @param applications List of applications.
     * @return ID of first accepted application.
     */
    long acceptApplications(List<Application> applications) throws DaoException;

}
