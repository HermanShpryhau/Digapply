package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Result;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ResultRowMapper implements RowMapper<Result> {
    @Override
    public Result mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Result result = new Result();
        result.setResultId(resultSet.getLong(Column.RESULT_ID));
        result.setApplicationId(resultSet.getLong(Column.APPLICATION_ID));
        result.setSubjectId(resultSet.getLong(Column.SUBJECT_ID));
        result.setScore(resultSet.getInt(Column.SCORE));
        result.setCertificateId(resultSet.getString(Column.CERTIFICATE_ID));
        return result;
    }
}
