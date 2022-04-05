package dev.shph.digapply.dao.impl;

import dev.shph.digapply.configuration.PersistenceConfiguration;
import dev.shph.digapply.dao.PreparedStatementParameterSetter;
import dev.shph.digapply.dao.mapper.Column;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.FacultyDao;
import dev.shph.digapply.dao.ParametrizedQuery;
import dev.shph.digapply.dao.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FacultyDaoImpl extends AbstractDao<Faculty> implements FacultyDao {
    private static final String SAVE_FACULTY_QUERY = "INSERT INTO Faculties (faculty_id, faculty_name, faculty_short_description, faculty_description, places, is_application_closed) VALUES (0, ?, ?, ?, ?, ?)";
    private static final String ADD_SUBJECT_TO_FACULTY_QUERY = "INSERT INTO Faculties_has_Subjects VALUES (?, ?)";
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

    @Autowired
    @Qualifier(PersistenceConfiguration.FACULTY_INSERT)
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    @Qualifier(PersistenceConfiguration.SUBJECT_INSERT)
    private SimpleJdbcInsert subjectJdbcInsert;

    public FacultyDaoImpl() {
        super(Table.FACULTY_TABLE);
    }

    @Override
    public long save(Faculty entity) throws DaoException {
        throw new DaoException("Unsupported operation for Faculties table.");
    }

    @Override
    @Transactional
    public long save(Faculty faculty, List<Long> subjectIds) throws DaoException {
        throwExceptionIfNull(faculty);
        throwExceptionIfNull(subjectIds);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Column.FACULTY_NAME, faculty.getFacultyName());
        parameters.put(Column.FACULTY_SHORT_DESCRIPTION, faculty.getFacultyShortDescription());
        parameters.put(Column.FACULTY_DESCRIPTION, faculty.getFacultyDescription());
        parameters.put(Column.FACULTY_PLACES, faculty.getPlaces());
        parameters.put(Column.IS_APPLICATION_CLOSED, faculty.isApplicationClosed());
        long facultyId = (long) jdbcInsert.executeAndReturnKey(parameters);
        for (Long id : subjectIds) {
            PreparedStatementParameterSetter parameterSetter =
                    new PreparedStatementParameterSetter(new Object[]{facultyId, id});
            jdbcTemplate.update(ADD_SUBJECT_TO_FACULTY_QUERY, parameterSetter::setValues);
        }
        return subjectIds.size();
    }

    @Override
    public Faculty findById(long id) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{id});
        return jdbcTemplate.query(FIND_FACULTY_BY_ID_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Faculty> findByPattern(String pattern, long page, long elementsPerPage) {
        long startIndex = (page - 1) * elementsPerPage;
        pattern = "%" + pattern + "%";
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{pattern, startIndex, elementsPerPage});
        return jdbcTemplate.query(FIND_BY_PATTERN_IN_NAME_QUERY, parameterSetter::setValues, mapper);
    }

    @Override
    public long getRowsCountForSearch(String pattern) {
        pattern = "%" + pattern + "%";
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{pattern});
        return jdbcTemplate.query(FIND_ALL_BY_PATTERN_IN_NAME_QUERY, parameterSetter::setValues, mapper).size();
    }

    @Override
    public long update(Faculty entity) throws DaoException {
        throwExceptionIfNull(entity);
        jdbcTemplate.update(
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
        jdbcTemplate.update(DELETE_FACULTY_QUERY, id);
        return id;
    }

    @Override
    public List<Faculty> findBestFaculties(int count) {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{count});
        return jdbcTemplate.query(FIND_BEST_FACULTIES_QUERY, parameterSetter::setValues, mapper);
    }
}
