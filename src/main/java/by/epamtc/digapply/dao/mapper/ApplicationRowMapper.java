package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.model.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationRowMapper implements RowMapper<Application> {
    @Override
    public Application map(ResultSet resultSet) throws SQLException {
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
