package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.RoleDao;
import by.epamtc.digapply.dao.Table;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.entity.Role;

import java.util.List;

public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM Roles WHERE role_id=?";

    public RoleDaoImpl() {
        super(RowMapperFactory.getInstance().getRoleRowMapper(), Table.ROLE_TABLE);
    }

    @Override
    public long save(Role entity) throws DaoException {
        throw new DaoException("Unsupported operation for Roles table");
    }

    @Override
    public Role findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_BY_ID_QUERY, id);
    }

    @Override
    public long update(Role entity) throws DaoException {
        throw new DaoException("Unsupported operation for Roles table");
    }

    @Override
    public List<Role> findAllOnPage(long page, long count) throws DaoException {
        throw new DaoException("Unsupported operation for Roles table");
    }

    @Override
    public long removeById(long id) throws DaoException {
        throw new DaoException("Unsupported operation for Roles table");
    }

    @Override
    public long remove(Role entity) throws DaoException {
        throw new DaoException("Unsupported operation for Roles table");
    }
}
