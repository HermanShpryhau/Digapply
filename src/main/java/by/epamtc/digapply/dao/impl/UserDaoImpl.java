package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.dao.Table;

import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String SAVE_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM Users WHERE deleted=false";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE user_id=? AND deleted=false";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM Users WHERE email=? AND deleted=false";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET password=?, name=?, surname=? WHERE user_id=? AND deleted=false";
    private static final String UPDATE_USER_ROLE_QUERY = "UPDATE Users SET role_id=? WHERE user_id=? AND deleted=false";
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
                entity.getSurname(),
                entity.getRoleId()
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
                entity.getPassword(),
                entity.getName(),
                entity.getSurname(),
                entity.getId()
        );
    }

    @Override
    public long updateUserRole(long userId, long roleId) throws DaoException {
        return jdbcOperator.executeUpdate(UPDATE_USER_ROLE_QUERY, roleId, userId);
    }

    @Override
    public long removeById(long id) throws DaoException {
        return jdbcOperator.executeUpdate(DELETE_USER_QUERY, id);
    }
}
