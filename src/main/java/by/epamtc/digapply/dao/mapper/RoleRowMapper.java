package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role map(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setRoleId(resultSet.getLong(Column.ROLE_ID));
        role.setRoleName(resultSet.getString(Column.ROLE_NAME));
        return role;
    }
}
