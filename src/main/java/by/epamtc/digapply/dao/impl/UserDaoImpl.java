package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.AbstractDao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.JdbcOperator;
import by.epamtc.digapply.dao.UserDao;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.mapper.factory.RowMapperFactory;
import by.epamtc.digapply.resource.Table;


public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String ADD_USER_QUERY = "INSERT INTO Users (user_id, email, password, name, surname, role_id) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE user_id=?";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM Users WHERE email=?";
    private static final String UPDATE_USER_QUERY = "UPDATE Users SET email=?, password=?, name=?, surname=?, role_id=? WHERE user_id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM Users WHERE user_id=?";

    public UserDaoImpl() {
        super(RowMapperFactory.getInstance().getUserRowMapper(), Table.USER_TABLE);
    }

    @Override
    public void add(User user) throws DaoException {
        throwExceptionIfNull(user);

        JdbcOperator<User> jdbcOperator = new JdbcOperator<>(mapper);
        jdbcOperator.executeUpdate(
                ADD_USER_QUERY,
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getRoleId()
        );
    }

    @Override
    public User findById(long id) throws DaoException {
        JdbcOperator<User> jdbcOperator = new JdbcOperator<>(mapper);
        return jdbcOperator.executeSingleEntityQuery(FIND_USER_BY_ID_QUERY, id);
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        JdbcOperator<User> jdbcOperator = new JdbcOperator<>(mapper);
        return jdbcOperator.executeSingleEntityQuery(FIND_USER_BY_EMAIL_QUERY, email);
    }

    @Override
    public void updateEntity(User entity) throws DaoException {
        throwExceptionIfNull(entity);

        JdbcOperator<User> jdbcOperator = new JdbcOperator<>(mapper);
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
        JdbcOperator<User> jdbcOperator = new JdbcOperator<>(mapper);
        jdbcOperator.executeUpdate(
                DELETE_USER_QUERY,
                id
        );
    }
}
