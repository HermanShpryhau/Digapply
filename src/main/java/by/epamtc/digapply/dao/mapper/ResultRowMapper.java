package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.model.Result;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultRowMapper implements RowMapper<Result> {
    @Override
    public Result map(ResultSet resultSet) throws SQLException {
        Result result = new Result();
        result.setResultId(resultSet.getLong(Column.RESULT_ID));
        result.setApplicationId(resultSet.getLong(Column.APPLICATION_ID));
        result.setSubjectId(resultSet.getLong(Column.SUBJECT_ID));
        result.setScore(resultSet.getInt(Column.SCORE));
        result.setCertificateId(resultSet.getString(Column.CERTIFICATE_ID));
        return result;
    }
}
