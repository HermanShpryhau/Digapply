package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
