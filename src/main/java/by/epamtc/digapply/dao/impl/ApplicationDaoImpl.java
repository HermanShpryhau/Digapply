package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.ApplicationDao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.ParametrizedQuery;
import by.epamtc.digapply.dao.Table;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Result;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImpl extends AbstractDao<Application> implements ApplicationDao {
    private static final String SAVE_APPLICATION_QUERY = "INSERT INTO Applications (application_id, user_id, faculty_id, apply_date, approved, approve_date) VALUES (0, ?, ?, CURRENT_TIMESTAMP(), false, NULL)";
    private static final String SAVE_RESULT_FOR_APPLICATION_QUERY = "INSERT INTO Results (result_id, application_id, subject_id, score, certificate_id) VALUES (0, (SELECT application_id FROM Applications WHERE user_id=? AND faculty_id=?), ?, ?, ?)";
    private static final String FIND_APPLICATION_BY_ID_QUERY = "SELECT * FROM Applications WHERE application_id=?";
    private static final String FIND_APPLICATION_BY_USER_QUERY = "SELECT * FROM Applications WHERE user_id=?";
    private static final String FIND_APPLICATIONS_BY_FACULTY_QUERY = "SELECT * FROM Applications WHERE faculty_id=?";
    private static final String UPDATE_APPLICATION_QUERY = "UPDATE Applications SET user_id=?, faculty_id=?, apply_date=?, approved=?, approve_date=? WHERE application_id=?";
    private static final String DELETE_APPLICATION_QUERY = "DELETE FROM Applications  WHERE application_id=?";

    public ApplicationDaoImpl() {
        super(RowMapperFactory.getInstance().getApplicationRowMapper(), Table.APPLICATION_TABLE);
    }

    @Override
    public long save(Application entity) throws DaoException {
        throw new DaoException("Unsupported save operation for Application entity.");
    }

    @Override
    public long save(Application entity, List<Result> results) throws DaoException {
        List<ParametrizedQuery> transactionQueries = new ArrayList<>();
        ParametrizedQuery saveApplicationQuery = new ParametrizedQuery(
                SAVE_APPLICATION_QUERY,
                new Object[] {entity.getUserId(), entity.getFacultyId()}
        );
        transactionQueries.add(saveApplicationQuery);
        for (Result result : results) {
            Object[] parameters = {
                    entity.getUserId(),
                    entity.getFacultyId(),
                    result.getSubjectId(),
                    result.getScore(),
                    result.getCertificateId()
            };
            ParametrizedQuery saveResultForApplication = new ParametrizedQuery(SAVE_RESULT_FOR_APPLICATION_QUERY, parameters);
            transactionQueries.add(saveResultForApplication);
        }
        return jdbcOperator.executeTransactionalUpdate(transactionQueries);
    }

    @Override
    public Application findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_APPLICATION_BY_ID_QUERY, id);
    }

    @Override
    public Application findByUserId(long userId) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_APPLICATION_BY_USER_QUERY, userId);
    }

    @Override
    public List<Application> findByFacultyId(long facultyId) throws DaoException {
        return jdbcOperator.executeQuery(FIND_APPLICATIONS_BY_FACULTY_QUERY, facultyId);
    }

    @Override
    public long updateEntity(Application entity) throws DaoException {
        jdbcOperator.executeUpdate(
                UPDATE_APPLICATION_QUERY,
                entity.getUserId(),
                entity.getFacultyId(),
                entity.getApplyDate(),
                entity.isApproved(),
                entity.getApproveDate(),
                entity.getApplicationId()
        );
        return entity.getId();
    }

    @Override
    public long removeById(long id) throws DaoException {
        jdbcOperator.executeUpdate(DELETE_APPLICATION_QUERY, id);
        return id;
    }
}
