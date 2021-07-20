package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.AbstractDao;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.FacultyDao;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;
import by.epamtc.digapply.dao.Table;

import java.util.List;

public class FacultyDaoImpl extends AbstractDao<Faculty> implements FacultyDao {
    private static final String SAVE_FACULTY_QUERY = "INSERT INTO Faculties (faculty_id, faculty_name, faculty_description, places) VALUES (0, ?, ?, ?)";
    private static final String FIND_FACULTY_BY_ID_QUERY = "SELECT * FROM Faculties WHERE faculty_id=?";
    private static final String UPDATE_FACULTY_QUERY = "UPDATE Faculties SET faculty_name=?, faculty_description=?, places=? WHERE faculty_id=?";
    private static final String DELETE_FACULTY_QUERY = "DELETE FROM Faculties WHERE faculty_id=?";
    private static final String FIND_BEST_FACULTIES_QUERY = "SELECT Faculties.faculty_id, faculty_name, faculty_short_description, faculty_description, places, COUNT(A.application_id) AS count\n" +
            "FROM Faculties\n" +
            "         JOIN Applications A on Faculties.faculty_id = A.faculty_id\n" +
            "GROUP BY Faculties.faculty_id\n" +
            "ORDER BY count DESC\n" +
            "LIMIT ?";

    public FacultyDaoImpl() {
        super(RowMapperFactory.getInstance().getFacultyRowMapper(), Table.FACULTY_TABLE);
    }

    @Override
    public void save(Faculty entity) throws DaoException {
        throwExceptionIfNull(entity);
        jdbcOperator.executeUpdate(
                SAVE_FACULTY_QUERY,
                entity.getFacultyName(),
                entity.getFacultyDescription(),
                entity.getPlaces()
        );
    }

    @Override
    public Faculty findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_FACULTY_BY_ID_QUERY, id);
    }

    @Override
    public void updateEntity(Faculty entity) throws DaoException {
        throwExceptionIfNull(entity);
        jdbcOperator.executeUpdate(
                UPDATE_FACULTY_QUERY,
                entity.getFacultyName(),
                entity.getFacultyDescription(),
                entity.getPlaces(),
                entity.getId()
        );
    }

    @Override
    public void removeById(long id) throws DaoException {
        jdbcOperator.executeUpdate(DELETE_FACULTY_QUERY, id);
    }

    @Override
    public List<Faculty> findBestFaculties(int count) throws DaoException {
        return jdbcOperator.executeQuery(FIND_BEST_FACULTIES_QUERY, count);
    }
}
