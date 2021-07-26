package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.ApplicationDao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.Table;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Result;

import java.util.List;

public class ApplicationDaoImpl extends AbstractDao<Application> implements ApplicationDao {
    private static final String SAVE_APPLICATION_QUERY = "INSERT INTO Applications (application_id, user_id, faculty_id, apply_date, approved, approve_date) VALUES (0, ?, ?, CURRENT_TIMESTAMP(), false, NULL)";
    private static final String SAVE_RESULT_FOR_APPLICATION_QUERY = "INSERT INTO Results (result_id, application_id, subject_id, score, certificate_id) VALUES (0, ?, ?, ?, ?)";
    private static final String FIND_APPLICATION_BY_ID_QUERY = "SELECT * FROM Application WHERE application_id=?";
    private static final String FIND_APPLICATION_BY_USER_FACULTY_QUERY = "SELECT * FROM Applications WHERE user_id=? AND faculty_id=?";
    private static final String UPDATE_APPLICATION_QUERY = "UPDATE Applications SET user_id=?, faculty_id=?, apply_date=?, approved=?, approve_date=? WHERE application_id=?";
    private static final String DELETE_APPLICATION_QUERY = "DELETE FROM Applications  WHERE application_id=?";

    public ApplicationDaoImpl() {
        super(RowMapperFactory.getInstance().getApplicationRowMapper(), Table.APPLICATION_TABLE);
    }

    @Override
    public void save(Application entity) throws DaoException {
        throw new DaoException("Unsupported save operation for Application entity.");
    }

    @Override
    public void save(Application entity, List<Result> results) throws DaoException {
        jdbcOperator.executeUpdate(
                SAVE_APPLICATION_QUERY,
                entity.getUserId(),
                entity.getFacultyId()
        );
        Application application = jdbcOperator.executeSingleEntityQuery(
                FIND_APPLICATION_BY_USER_FACULTY_QUERY,
                entity.getUserId(),
                entity.getFacultyId()
        );
        for (Result result : results) {
            jdbcOperator.executeUpdate(
                    SAVE_RESULT_FOR_APPLICATION_QUERY,
                    application.getApplicationId(),
                    result.getSubjectId(),
                    result.getScore(),
                    result.getCertificateId()
            );
        }
    }

    @Override
    public Application findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_APPLICATION_BY_ID_QUERY, id);
    }

    @Override
    public Application findByUserAndFaculty(long userId, long facultyId) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_APPLICATION_BY_USER_FACULTY_QUERY, userId, facultyId);
    }

    @Override
    public void updateEntity(Application entity) throws DaoException {
        jdbcOperator.executeUpdate(
                UPDATE_APPLICATION_QUERY,
                entity.getUserId(),
                entity.getFacultyId(),
                entity.getApplyDate(),
                entity.isApproved(),
                entity.getApproveDate(),
                entity.getApplicationId()
        );
    }

    @Override
    public void removeById(long id) throws DaoException {
        jdbcOperator.executeUpdate(DELETE_APPLICATION_QUERY, id);
    }
}
