package dev.shph.digapply.dao.impl;

import dev.shph.digapply.configuration.PersistenceConfiguration;
import dev.shph.digapply.dao.ApplicationDao;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.ParametrizedQuery;
import dev.shph.digapply.dao.PreparedStatementParameterSetter;
import dev.shph.digapply.dao.Table;
import dev.shph.digapply.dao.mapper.Column;
import dev.shph.digapply.entity.Application;
import dev.shph.digapply.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ApplicationDaoImpl extends AbstractDao<Application> implements ApplicationDao {
    private static final String FIND_APPLICATION_BY_ID_QUERY = "SELECT * FROM Applications WHERE application_id=?";
    private static final String FIND_APPLICATION_BY_USER_QUERY = "SELECT * FROM Applications WHERE user_id=?";
    private static final String FIND_APPLICATIONS_BY_FACULTY_QUERY = "SELECT * FROM Applications WHERE faculty_id=?";
    private static final String FIND_ACCEPTED_BY_FACULTY_QUERY = "SELECT * FROM Applications JOIN Accepted_students A_s on Applications.application_id = A_s.application_id WHERE Applications.faculty_id=?";
    private static final String UPDATE_APPLICATION_QUERY = "UPDATE Applications SET user_id=?, faculty_id=?, apply_date=?, approved=?, approve_date=? WHERE application_id=?";
    private static final String UPDATE_RESULT_QUERY = "UPDATE Results SET score=?, certificate_id=? WHERE application_id=? AND subject_id=?";
    private static final String ACCEPT_APPLICATION_QUERY = "INSERT INTO Accepted_students (accepted_id, application_id) VALUES (0, ?)";
    private static final String DELETE_APPLICATION_QUERY = "DELETE FROM Applications  WHERE application_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(PersistenceConfiguration.APPLICATION_INSERT)
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    @Qualifier(PersistenceConfiguration.RESULT_INSERT)
    private SimpleJdbcInsert resultJdbcInsert;

    public ApplicationDaoImpl() {
        super(Table.APPLICATION_TABLE);
    }

    @Override
    public long save(Application entity) throws DaoException {
        throw new DaoException("Unsupported save operation for Application entity.");
    }

    @Override
    @Transactional
    public long save(Application entity, List<Result> results) throws DaoException {
        for (Result result : results) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(Column.USER_ID, entity.getUserId());
            parameters.put(Column.FACULTY_ID, entity.getFacultyId());
            parameters.put(Column.SUBJECT_ID, result.getSubjectId());
            parameters.put(Column.SCORE, result.getScore());
            parameters.put(Column.CERTIFICATE_ID, result.getCertificateId());
            resultJdbcInsert.execute(parameters);
        }
        Map<String, Object> params = new HashMap<>();
        params.put(Column.USER_ID, entity.getUserId());
        params.put(Column.FACULTY_ID, entity.getFacultyId());
        return jdbcInsert.execute(params);
    }

    @Override
    public Application findById(long id) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{id});
        return jdbcTemplate.query(FIND_APPLICATION_BY_ID_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Application findByUserId(long userId) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{userId});
        return jdbcTemplate.query(FIND_APPLICATION_BY_USER_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Application> findByFacultyId(long facultyId) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{facultyId});
        return jdbcTemplate.query(FIND_APPLICATIONS_BY_FACULTY_QUERY, parameterSetter::setValues, mapper);
    }

    @Override
    public List<Application> findAcceptedByFacultyId(long facultyId) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{facultyId});
        return jdbcTemplate.query(FIND_ACCEPTED_BY_FACULTY_QUERY, parameterSetter::setValues, mapper);
    }

    @Override
    public long update(Application entity) throws DaoException {
        jdbcTemplate.update(
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
    @Transactional
    public long update(long id, List<Result> results) throws DaoException {
        for (Result result : results) {
            jdbcTemplate.update(
                    UPDATE_RESULT_QUERY,
                    result.getScore(),
                    result.getCertificateId(),
                    id,
                    result.getSubjectId()
            );
        }
        return id;
    }

    @Override
    @Transactional
    public long acceptApplications(List<Application> applications) throws DaoException {
        int affectedRows = 0;
        for (Application application : applications) {
            jdbcTemplate.update(ACCEPT_APPLICATION_QUERY, application.getId());
            affectedRows++;
        }
        return affectedRows;
    }

    @Override
    public long removeById(long id) throws DaoException {
        jdbcTemplate.update(DELETE_APPLICATION_QUERY, id);
        return id;
    }
}
