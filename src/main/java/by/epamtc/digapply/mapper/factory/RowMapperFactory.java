package by.epamtc.digapply.mapper.factory;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.mapper.FacultyRowMapper;
import by.epamtc.digapply.mapper.RowMapper;
import by.epamtc.digapply.mapper.SubjectRowMapper;
import by.epamtc.digapply.mapper.UserRowMapper;

public class RowMapperFactory {
    private final RowMapper<User> userRowMapper = new UserRowMapper();
    private final RowMapper<Faculty> facultyRowMapper = new FacultyRowMapper();
    private final RowMapper<Subject> subjectRowMapper = new SubjectRowMapper();

    public static RowMapperFactory getInstance() {
        return Holder.INSTANCE;
    }

    public RowMapper<User> getUserRowMapper() {
        return userRowMapper;
    }

    public RowMapper<Faculty> getFacultyRowMapper() {
        return facultyRowMapper;
    }

    public RowMapper<Subject> getSubjectRowMapper() {
        return subjectRowMapper;
    }

    private static class Holder {
        private static final RowMapperFactory INSTANCE = new RowMapperFactory();
    }
}
