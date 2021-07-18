package by.epamtc.digapply.mapper;

import by.epamtc.digapply.entity.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyRowMapper implements RowMapper<Faculty> {
    @Override
    public Faculty map(ResultSet resultSet) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(resultSet.getLong(Column.FACULTY_ID));
        faculty.setFacultyName(resultSet.getString(Column.FACULTY_NAME));
        faculty.setFacultyShortDescription(resultSet.getString(Column.FACULTY_SHORT_DESCRIPTION));
        faculty.setFacultyDescription(resultSet.getString(Column.FACULTY_DESCRIPTION));
        faculty.setPlaces(resultSet.getInt(Column.FACULTY_PLACES));
        return faculty;
    }
}
