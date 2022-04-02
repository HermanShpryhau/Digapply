package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public UserRowMapper() {}

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong(Column.USER_ID));
        user.setEmail(resultSet.getString(Column.USER_EMAIL));
        user.setPassword(resultSet.getString(Column.USER_PASSWORD));
        user.setName(resultSet.getString(Column.USER_NAME));
        user.setSurname(resultSet.getString(Column.USER_SURNAME));
        user.setRole(UserRole.valueOf(resultSet.getString(Column.ROLE_NAME).toUpperCase()));
        user.setRoleId(resultSet.getLong(Column.USER_ROLE));
        return user;
    }
}
