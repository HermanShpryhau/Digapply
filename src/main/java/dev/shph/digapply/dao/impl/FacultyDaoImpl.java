package dev.shph.digapply.dao.impl;

import dev.shph.digapply.dao.*;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.dao.mapper.RowMapperFactory;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.FacultyDao;
import dev.shph.digapply.dao.ParametrizedQuery;
import dev.shph.digapply.dao.Table;

import java.util.ArrayList;
import java.util.List;

public class FacultyDaoImpl extends AbstractDao<Faculty> implements FacultyDao {
    private static final String SAVE_FACULTY_QUERY = "INSERT INTO Faculties (faculty_id, faculty_name, faculty_short_description, faculty_description, places, is_application_closed) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String ADD_SUBJECT_TO_FACULTY_QUERY = "INSERT INTO Faculties_has_Subjects (faculties_faculty_id, subjects_subject_id) VALUES ((SELECT faculty_id FROM Faculties WHERE faculty_name=?), ?)";
    private static final String FIND_FACULTY_BY_ID_QUERY = "SELECT * FROM Faculties WHERE faculty_id=?";
    private static final String UPDATE_FACULTY_QUERY = "UPDATE Faculties SET faculty_name=?, faculty_short_description=?, faculty_description=?, places=?, is_application_closed=? WHERE faculty_id=?";
    private static final String DELETE_FACULTY_QUERY = "DELETE FROM Faculties WHERE faculty_id=?";
    private static final String FIND_BY_PATTERN_IN_NAME_QUERY = "SELECT * FROM Faculties WHERE faculty_name LIKE ? LIMIT ?, ?";
    private static final String FIND_ALL_BY_PATTERN_IN_NAME_QUERY = "SELECT * FROM Faculties WHERE faculty_name LIKE ?";
    private static final String FIND_BEST_FACULTIES_QUERY = "SELECT Faculties.faculty_id, faculty_name, faculty_short_description, faculty_description, places, is_application_closed, COUNT(A.application_id) AS count\n" +
            "FROM Faculties\n" +
            "JOIN Applications A on Faculties.faculty_id = A.faculty_id\n" +
            "GROUP BY Faculties.faculty_id\n" +
            "ORDER BY count DESC\n" +
            "LIMIT ?";

    public FacultyDaoImpl() {
        super(RowMapperFactory.getInstance().getFacultyRowMapper(), Table.FACULTY_TABLE);
    }

    @Override
    public long save(Faculty entity) throws DaoException {
        throw new DaoException("Unsupported operation for Faculties table.");
    }

    @Override
    public long save(Faculty faculty, List<Long> subjectIds) throws DaoException {
        throwExceptionIfNull(faculty);
        throwExceptionIfNull(subjectIds);

        List<ParametrizedQuery> queries = new ArrayList<>();
        Object[] saveQueryParams = {faculty.getFacultyName(), faculty.getFacultyShortDescription(), faculty.getFacultyDescription(), faculty.getPlaces(), faculty.isApplicationClosed()};
        queries.add(new ParametrizedQuery(SAVE_FACULTY_QUERY, saveQueryParams));
        for (Long id : subjectIds) {
            Object[] addSubjectQueryParams = {faculty.getFacultyName(), id};
            queries.add(new ParametrizedQuery(ADD_SUBJECT_TO_FACULTY_QUERY, addSubjectQueryParams));
        }

        return jdbcOperator.executeTransactionalUpdate(queries);
    }

    @Override
    public Faculty findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_FACULTY_BY_ID_QUERY, id);
    }

    @Override
    public List<Faculty> findByPattern(String pattern, long page, long elementsPerPage) throws DaoException {
        long startIndex = (page - 1) * elementsPerPage;
        pattern = "%" + pattern + "%";
        return jdbcOperator.executeQuery(FIND_BY_PATTERN_IN_NAME_QUERY, pattern, startIndex, elementsPerPage);
    }

    @Override
    public long getRowsCountForSearch(String pattern) throws DaoException {
        pattern = "%" + pattern + "%";
        return jdbcOperator.executeQuery(FIND_ALL_BY_PATTERN_IN_NAME_QUERY, pattern).size();
    }

    @Override
    public long update(Faculty entity) throws DaoException {
        throwExceptionIfNull(entity);
        jdbcOperator.executeUpdate(
                UPDATE_FACULTY_QUERY,
                entity.getFacultyName(),
                entity.getFacultyShortDescription(),
                entity.getFacultyDescription(),
                entity.getPlaces(),
                entity.isApplicationClosed(),
                entity.getId()
        );
        return entity.getId();
    }

    @Override
    public long removeById(long id) throws DaoException {
        jdbcOperator.executeUpdate(DELETE_FACULTY_QUERY, id);
        return id;
    }

    @Override
    public List<Faculty> findBestFaculties(int count) throws DaoException {
        return jdbcOperator.executeQuery(FIND_BEST_FACULTIES_QUERY, count);
    }
}
