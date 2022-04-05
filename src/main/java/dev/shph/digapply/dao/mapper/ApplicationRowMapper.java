package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Application;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ApplicationRowMapper implements RowMapper<Application> {
    @Override
    public Application mapRow(ResultSet resultSet, int rowNum)throws SQLException {
        Application application = new Application();
        application.setApplicationId(resultSet.getLong(Column.APPLICATION_ID));
        application.setUserId(resultSet.getLong(Column.USER_ID));
        application.setFacultyId(resultSet.getLong(Column.FACULTY_ID));
        application.setApplyDate(resultSet.getTimestamp(Column.APPLY_DATE));
        application.setApproved(resultSet.getBoolean(Column.APPROVED));
        application.setApproveDate(resultSet.getTimestamp(Column.APPROVE_DATE));
        return application;
    }
}
