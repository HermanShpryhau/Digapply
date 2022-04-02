package dev.shph.digapply.dao.impl;

import dev.shph.digapply.configuration.PersistenceConfiguration;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.PreparedStatementParameterSetter;
import dev.shph.digapply.dao.ResultDao;
import dev.shph.digapply.dao.Table;
import dev.shph.digapply.dao.mapper.Column;
import dev.shph.digapply.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ResultDaoImpl extends AbstractDao<Result> implements ResultDao {
    private static final String SAVE_RESULT_QUERY = "INSERT INTO Results (result_id, application_id, subject_id, score, certificate_id) VALUES (0, ?, ?, ?, ?)";
    private static final String FIND_RESULT_BY_ID_QUERY = "SELECT * FROM Results WHERE result_id=?";
    private static final String FIND_BY_APPLICATION_ID_QUERY = "SELECT * FROM Results WHERE application_id=?";
    private static final String UPDATE_RESULT_QUERY = "UPDATE Results SET application_id=?, subject_id=?, score=?, certificate_id=? WHERE result_id=?";
    private static final String DELETE_APPLICATION_QUERY = "DELETE FROM Results WHERE result_id=?";

    @Autowired
    @Qualifier(PersistenceConfiguration.RESULT_INSERT)
    private SimpleJdbcInsert jdbcInsert;

    public ResultDaoImpl() {
        super( Table.RESULT_TABLE);
    }

    @Override
    public long save(Result entity) throws DaoException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Column.APPLICATION_ID, entity.getApplicationId());
        parameters.put(Column.SUBJECT_ID, entity.getSubjectId());
        parameters.put(Column.SCORE, entity.getScore());
        parameters.put(Column.CERTIFICATE_ID, entity.getCertificateId());

        return (long) jdbcInsert.executeAndReturnKey(parameters);
    }

    @Override
    public Result findById(long id) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{id});
        return jdbcTemplate.query(FIND_RESULT_BY_ID_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public long update(Result entity) throws DaoException {
        jdbcTemplate.update(
                UPDATE_RESULT_QUERY,
                entity.getApplicationId(),
                entity.getSubjectId(),
                entity.getScore(),
                entity.getCertificateId(),
                entity.getResultId()
        );
        return entity.getId();
    }

    @Override
    public long removeById(long id) throws DaoException {
        jdbcTemplate.update(DELETE_APPLICATION_QUERY, id);
        return id;
    }

    @Override
    public List<Result> findByApplicationId(long applicationId) {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{applicationId});
        return jdbcTemplate.query(FIND_BY_APPLICATION_ID_QUERY, parameterSetter::setValues, mapper);
    }
}
