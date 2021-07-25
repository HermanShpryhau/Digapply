package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.SubjectDao;
import by.epamtc.digapply.dao.Table;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.dao.mapper.RowMapperFactory;

import java.util.List;

/**
 * Implementation of {@link SubjectDao} interface
 */
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

    public SubjectDaoImpl() {
        super(RowMapperFactory.getInstance().getSubjectRowMapper(), Table.SUBJECT_TABLE);
    }

    @Override
    public void save(Subject entity) throws DaoException {
        jdbcOperator.executeUpdate(SAVE_SUBJECT_QUERY, entity.getSubjectName());
    }

    @Override
    public Subject findById(long id) throws DaoException {
        return jdbcOperator.executeSingleEntityQuery(FIND_SUBJECT_BY_ID_QUERY, id);
    }

    @Override
    public void updateEntity(Subject entity) throws DaoException {
        jdbcOperator.executeUpdate(UPDATE_SUBJECT_QUERY, entity.getSubjectName(), entity.getId());
    }

    @Override
    public void removeById(long id) throws DaoException {
        jdbcOperator.executeUpdate(DELETE_SUBJECT_BY_ID_QUERY, id);
    }

    @Override
    public List<Subject> findSubjectsByFaculty(long facultyId) throws DaoException {
        return jdbcOperator.executeQuery(FIND_SUBJECTS_BY_FACULTY_QUERY, facultyId);
    }
}
