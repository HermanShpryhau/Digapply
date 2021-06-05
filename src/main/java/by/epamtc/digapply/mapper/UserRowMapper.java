package by.epamtc.digapply.mapper;

import by.epamtc.digapply.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public UserRowMapper() {}

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setRoleId(resultSet.getLong("role_id"));
        return user;
    }
}
