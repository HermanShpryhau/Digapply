package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Faculty;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FacultyRowMapper implements RowMapper<Faculty> {
    @Override
    public Faculty mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(resultSet.getLong(Column.FACULTY_ID));
        faculty.setFacultyName(resultSet.getString(Column.FACULTY_NAME));
        faculty.setFacultyShortDescription(resultSet.getString(Column.FACULTY_SHORT_DESCRIPTION));
        faculty.setFacultyDescription(resultSet.getString(Column.FACULTY_DESCRIPTION));
        faculty.setPlaces(resultSet.getInt(Column.FACULTY_PLACES));
        faculty.setApplicationClosed(resultSet.getBoolean(Column.IS_APPLICATION_CLOSED));
        return faculty;
    }
}
