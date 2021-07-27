package by.epamtc.digapply.dao;

import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Result;

import java.util.List;

public interface ApplicationDao extends Dao<Application> {

    void save(Application entity, List<Result> results) throws DaoException;

    Application findByUserId(long userId) throws DaoException;

}
