package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Result;

import java.util.List;

public interface ResultDao extends Dao<Result> {

    List<Result> findByApplicationId(long applicationId) throws DaoException;

}
