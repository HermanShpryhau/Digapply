package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Subject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubjectRowMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(resultSet.getLong(Column.SUBJECT_ID));
        subject.setSubjectName(resultSet.getString(Column.SUBJECT_NAME));
        return subject;
    }
}
