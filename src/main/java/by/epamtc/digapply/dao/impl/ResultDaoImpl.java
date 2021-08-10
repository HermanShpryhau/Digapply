package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.ResultDao;
import by.epamtc.digapply.dao.Table;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.entity.Result;

import java.util.List;

public class ResultDaoImpl extends AbstractDao<Result> implements ResultDao {
    private static final String SAVE_RESULT_QUERY = "INSERT INTO Results (result_id, application_id, subject_id, score, certificate_id) VALUES (0, ?, ?, ?, ?)";
    private static final String FIND_RESULT_BY_ID_QUERY = "SELECT * FROM Results WHERE result_id=?";
    private static final String FIND_BY_APPLICATION_ID_QUERY = "SELECT * FROM Results WHERE application_id=?";
    private static final String UPDATE_RESULT_QUERY = "UPDATE Results SET application_id=?, subject_id=?, score=?, certificate_id=? WHERE result_id=?";
    private static final String DELETE_APPLICATION_QUERY = "DELETE FROM Results WHERE result_id=?";

    public ResultDaoImpl() {
        super(RowMapperFactory.getInstance().getResultRowMapper(), Table.RESULT_TABLE);
    }

    @Override
    public long save(Result entity) throws DaoException {
        return jdbcOperator.executeUpdate(
                SAVE_RESULT_QUERY,
                entity.getApplicationId(),
                entity.getSubjectId(),
                entity.getScore(),
                entity.getCertificateId()
        );
    }

    @Override
    public Result findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_RESULT_BY_ID_QUERY, id);
    }

    @Override
    public long update(Result entity) throws DaoException {
        jdbcOperator.executeUpdate(
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
        jdbcOperator.executeUpdate(DELETE_APPLICATION_QUERY, id);
        return id;
    }

    @Override
    public List<Result> findByApplicationId(long applicationId) throws DaoException {
        return jdbcOperator.executeQuery(FIND_BY_APPLICATION_ID_QUERY, applicationId);
    }
}
