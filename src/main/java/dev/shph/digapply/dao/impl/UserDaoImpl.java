package dev.shph.digapply.dao.impl;

import dev.shph.digapply.configuration.PersistenceConfiguration;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.PreparedStatementParameterSetter;
import dev.shph.digapply.dao.UserDao;
import dev.shph.digapply.dao.mapper.Column;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.dao.Table;
import dev.shph.digapply.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String SAVE_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, 2)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM Users JOIN Roles R on R.role_id = Users.role_id WHERE deleted=false";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users JOIN Roles R on R.role_id = Users.role_id WHERE user_id=? AND deleted=false";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM Users JOIN Roles R on R.role_id = Users.role_id WHERE email=? AND deleted=false";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET  name=?, surname=? WHERE user_id=? AND deleted=false";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE Users SET password=? WHERE user_id=? AND deleted=false";
    private static final String UPDATE_USER_ROLE_QUERY = "UPDATE Users SET role_id=(SELECT role_id FROM Roles WHERE role_name=?) WHERE user_id=? AND deleted=false";
    private static final String DELETE_USER_QUERY = "UPDATE Users SET deleted=true WHERE user_id=?";

    @Autowired
    @Qualifier(PersistenceConfiguration.USER_INSERT)
    private SimpleJdbcInsert jdbcInsert;

    public UserDaoImpl() {
        super(Table.USER_TABLE);
    }

    @Override
    public long save(User entity) throws DaoException {
        throwExceptionIfNull(entity);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Column.USER_EMAIL, entity.getEmail());
        parameters.put(Column.USER_PASSWORD, entity.getPassword());
        parameters.put(Column.USER_NAME, entity.getName());
        parameters.put(Column.USER_SURNAME, entity.getSurname());
        return (long) jdbcInsert.executeAndReturnKey(parameters);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    @Override
    public User findById(long id) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{id});
        return jdbcTemplate.query(FIND_USER_BY_ID_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{email});
        return jdbcTemplate.query(FIND_USER_BY_EMAIL_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public long update(User entity) throws DaoException {
        throwExceptionIfNull(entity);
        return jdbcTemplate.update(
                UPDATE_USER_QUERY,
                entity.getName(),
                entity.getSurname(),
                entity.getId()
        );
    }

    @Override
    public long updatePassword(long userId, String password) throws DaoException {
        throwExceptionIfNull(password);
        return jdbcTemplate.update(
                UPDATE_PASSWORD_QUERY,
                password,
                userId
        );
    }

    @Override
    public long updateUserRole(long userId, UserRole role) throws DaoException {
        throwExceptionIfNull(role);
        return jdbcTemplate.update(
                UPDATE_USER_ROLE_QUERY,
                role.name(),
                userId
        );
    }

    @Override
    public long removeById(long id) throws DaoException {
        return jdbcTemplate.update(DELETE_USER_QUERY, id);
    }
}
