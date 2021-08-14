package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Result;

import java.util.List;

public interface ResultDao extends Dao<Result> {

    /**
     * Finds all results for given application.
     * @param applicationId ID of application.
     * @return List of results for application.
     */
    List<Result> findByApplicationId(long applicationId) throws DaoException;

}
