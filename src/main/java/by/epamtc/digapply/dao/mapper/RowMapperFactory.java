package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.entity.*;

public class RowMapperFactory {
    private final RowMapper<User> userRowMapper = new UserRowMapper();
    private final RowMapper<Faculty> facultyRowMapper = new FacultyRowMapper();
    private final RowMapper<Subject> subjectRowMapper = new SubjectRowMapper();
    private final RowMapper<Application> applicationRowMapper = new ApplicationRowMapper();
    private final RowMapper<Result> resultRowMapper = new ResultRowMapper();

    private RowMapperFactory() {
    }

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

    public RowMapper<Application> getApplicationRowMapper() {
        return applicationRowMapper;
    }

    public RowMapper<Result> getResultRowMapper() {
        return resultRowMapper;
    }

    private static class Holder {
        private static final RowMapperFactory INSTANCE = new RowMapperFactory();
    }
}
