package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.entity.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation for Application entity.
 */
public class ApplicationRowMapper implements RowMapper<Application> {
    @Override
    public Application map(ResultSet resultSet) throws SQLException {
        Application application = new Application();
        application.setApplicationId(resultSet.getInt("application_id"));
        application.setUserId(resultSet.getInt("user_id"));
        application.setFacultyId(resultSet.getInt("faculty_id"));
        application.setApplyDate(resultSet.getTimestamp("apply_date"));
        application.setApproved(resultSet.getBoolean("approved"));
        application.setApproveDate(resultSet.getTimestamp("approve_date"));
        return application;
    }
}
