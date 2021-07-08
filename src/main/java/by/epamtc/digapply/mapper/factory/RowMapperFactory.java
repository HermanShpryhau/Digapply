package by.epamtc.digapply.mapper.factory;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.mapper.RowMapper;
import by.epamtc.digapply.mapper.UserRowMapper;

import javax.xml.ws.Holder;

public class RowMapperFactory {
    private final RowMapper<User> userRowMapper = new UserRowMapper();

    private static class Holder {
        private static final RowMapperFactory INSTANCE = new RowMapperFactory();
    }

    public static RowMapperFactory getInstance() {
        return Holder.INSTANCE;
    }

    public RowMapper<User> getUserRowMapper() {
        return userRowMapper;
    }
}
