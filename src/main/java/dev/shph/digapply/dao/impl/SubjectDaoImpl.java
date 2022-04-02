package dev.shph.digapply.dao.impl;

import dev.shph.digapply.configuration.PersistenceConfiguration;
import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.PreparedStatementParameterSetter;
import dev.shph.digapply.dao.SubjectDao;
import dev.shph.digapply.dao.Table;
import dev.shph.digapply.dao.mapper.Column;
import dev.shph.digapply.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDaoImpl extends AbstractDao<Subject> implements SubjectDao {
    private static final String SAVE_SUBJECT_QUERY = "INSERT INTO Subjects (subject_id, subject_name) VALUES (0, ?)";
    private static final String FIND_SUBJECT_BY_ID_QUERY = "SELECT * FROM Subjects WHERE subject_id=?";
    private static final String UPDATE_SUBJECT_QUERY = "UPDATE Subjects SET subject_name=? WHERE subject_id=?";
    private static final String DELETE_SUBJECT_BY_ID_QUERY = "DELETE FROM Subjects WHERE subject_id=?";
    private static final String FIND_SUBJECTS_BY_FACULTY_QUERY = "SELECT subject_id, subject_name\n" +
            "FROM Subjects\n" +
            "JOIN Faculties_has_Subjects FhS on Subjects.subject_id = FhS.Subjects_subject_id\n" +
            "JOIN Faculties F on F.faculty_id = FhS.Faculties_faculty_id\n" +
            "WHERE faculty_id=?";

    @Autowired
    @Qualifier(PersistenceConfiguration.SUBJECT_INSERT)
    private SimpleJdbcInsert jdbcInsert;

    public SubjectDaoImpl() {
        super(Table.SUBJECT_TABLE);
    }

    @Override
    public long save(Subject entity) throws DaoException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Column.SUBJECT_NAME, entity.getSubjectName());
        return (long) jdbcInsert.executeAndReturnKey(parameters);
    }

    @Override
    public Subject findById(long id) throws DaoException {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{id});
        return jdbcTemplate.query(FIND_SUBJECT_BY_ID_QUERY, parameterSetter::setValues, mapper).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Subject> findSubjectsByFaculty(long facultyId) {
        PreparedStatementParameterSetter parameterSetter =
                new PreparedStatementParameterSetter(new Object[]{facultyId});
        return jdbcTemplate.query(FIND_SUBJECTS_BY_FACULTY_QUERY, parameterSetter::setValues, mapper);
    }

    @Override
    public long update(Subject entity) throws DaoException {
        jdbcTemplate.update(UPDATE_SUBJECT_QUERY, entity.getSubjectName(), entity.getId());
        return entity.getId();
    }

    @Override
    public long removeById(long id) throws DaoException {
        jdbcTemplate.update(DELETE_SUBJECT_BY_ID_QUERY, id);
        return id;
    }
}
