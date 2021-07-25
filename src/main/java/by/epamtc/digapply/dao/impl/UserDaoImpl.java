package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.dao.Table;

/**
 * Implementation of {@link UserDao} interface
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String SAVE_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE user_id=?";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM Users WHERE email=?";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET email=?, password=?, name=?, surname=?, role_id=? WHERE user_id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM Users WHERE user_id=?";

    public UserDaoImpl() {
        super(RowMapperFactory.getInstance().getUserRowMapper(), Table.USER_TABLE);
    }

    @Override
    public void save(User entity) throws DaoException {
        throwExceptionIfNull(entity);
        jdbcOperator.executeUpdate(
                SAVE_USER_QUERY,
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getSurname(),
                entity.getRoleId()
        );
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
    public void updateEntity(User entity) throws DaoException {
        throwExceptionIfNull(entity);

        jdbcOperator.executeUpdate(
                UPDATE_USER_QUERY,
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getSurname(),
                entity.getRoleId(),
                entity.getId()
        );
    }

    @Override
    public void removeById(long id) throws DaoException {
        jdbcOperator.executeUpdate(DELETE_USER_QUERY, id);
    }
}
