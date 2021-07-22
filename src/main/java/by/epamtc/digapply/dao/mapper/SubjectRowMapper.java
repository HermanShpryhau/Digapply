package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.entity.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRowMapper implements RowMapper<Subject> {
    @Override
    public Subject map(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(resultSet.getLong(Column.SUBJECT_ID));
        subject.setSubjectName(resultSet.getString(Column.SUBJECT_NAME));
        return subject;
    }
}
