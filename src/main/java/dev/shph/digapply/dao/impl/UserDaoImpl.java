package dev.shph.digapply.dao.impl;

import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.UserDao;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.dao.mapper.RowMapperFactory;
import dev.shph.digapply.dao.Table;
import dev.shph.digapply.entity.UserRole;

import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String SAVE_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, 2)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM Users JOIN Roles R on R.role_id = Users.role_id WHERE deleted=false";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users JOIN Roles R on R.role_id = Users.role_id WHERE user_id=? AND deleted=false";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM Users JOIN Roles R on R.role_id = Users.role_id WHERE email=? AND deleted=false";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET  name=?, surname=? WHERE user_id=? AND deleted=false";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE Users SET password=? WHERE user_id=? AND deleted=false";
    private static final String UPDATE_USER_ROLE_QUERY = "UPDATE Users SET role_id=(SELECT role_id FROM Roles WHERE role_name=?) WHERE user_id=? AND deleted=false";
    private static final String DELETE_USER_QUERY = "UPDATE Users SET deleted=true WHERE user_id=?";

    public UserDaoImpl() {
        super(RowMapperFactory.getInstance().getUserRowMapper(), Table.USER_TABLE);
    }

    @Override
    public long save(User entity) throws DaoException {
        throwExceptionIfNull(entity);
        return jdbcOperator.executeUpdate(
                SAVE_USER_QUERY,
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getSurname()
        );
    }

    @Override
    public List<User> findAll() throws DaoException {
        return jdbcOperator.executeQuery(FIND_ALL_QUERY);
    }

    @Override
    public User findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_USER_BY_ID_QUERY, id);
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_USER_BY_EMAIL_QUERY, email);
    }

    @Override
    public long update(User entity) throws DaoException {
        throwExceptionIfNull(entity);
        return jdbcOperator.executeUpdate(
                UPDATE_USER_QUERY,
                entity.getName(),
                entity.getSurname(),
                entity.getId()
        );
    }

    @Override
    public long updatePassword(long userId, String password) throws DaoException {
        throwExceptionIfNull(password);
        return jdbcOperator.executeUpdate(
                UPDATE_PASSWORD_QUERY,
                password,
                userId
        );
    }

    @Override
    public long updateUserRole(long userId, UserRole role) throws DaoException {
        throwExceptionIfNull(role);
        return jdbcOperator.executeUpdate(
                UPDATE_USER_ROLE_QUERY,
                role.name(),
                userId
        );
    }

    @Override
    public long removeById(long id) throws DaoException {
        return jdbcOperator.executeUpdate(DELETE_USER_QUERY, id);
    }
}
