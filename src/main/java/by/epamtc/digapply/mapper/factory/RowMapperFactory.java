package by.epamtc.digapply.mapper.factory;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.mapper.FacultyRowMapper;
import by.epamtc.digapply.mapper.RowMapper;
import by.epamtc.digapply.mapper.UserRowMapper;

import javax.xml.ws.Holder;

public class RowMapperFactory {
    private final RowMapper<User> userRowMapper = new UserRowMapper();
    private final RowMapper<Faculty> facultyRowMapper = new FacultyRowMapper();

    public static RowMapperFactory getInstance() {
        return Holder.INSTANCE;
    }

    public RowMapper<User> getUserRowMapper() {
        return userRowMapper;
    }

    public RowMapper<Faculty> getFacultyRowMapper() {
        return facultyRowMapper;
    }

    private static class Holder {
        private static final RowMapperFactory INSTANCE = new RowMapperFactory();
    }
}
