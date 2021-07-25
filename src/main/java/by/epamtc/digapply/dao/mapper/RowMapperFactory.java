package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.entity.User;

/**
 * Thread-safe singleton {@link RowMapper} implementations provider.
 */
public class RowMapperFactory {
    private final RowMapper<User> userRowMapper = new UserRowMapper();
    private final RowMapper<Faculty> facultyRowMapper = new FacultyRowMapper();
    private final RowMapper<Subject> subjectRowMapper = new SubjectRowMapper();

    /**
     * Gets instance of {@link DaoFactory}.
     * @return Instance of singleton.
     */
    public static RowMapperFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets implementation of {@link RowMapper}
     * @return {@link RowMapper} implementation for {@link User} entities.
     */
    public RowMapper<User> getUserRowMapper() {
        return userRowMapper;
    }

    /**
     * Gets implementation of {@link RowMapper}
     * @return {@link RowMapper} implementation for {@link Faculty} entities.
     */
    public RowMapper<Faculty> getFacultyRowMapper() {
        return facultyRowMapper;
    }

    /**
     * Gets implementation of {@link RowMapper}
     * @return {@link RowMapper} implementation for {@link Subject} entities.
     */
    public RowMapper<Subject> getSubjectRowMapper() {
        return subjectRowMapper;
    }

    private static class Holder {
        private static final RowMapperFactory INSTANCE = new RowMapperFactory();
    }
}
