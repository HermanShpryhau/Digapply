package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.impl.FacultyDaoImpl;
import by.epamtc.digapply.dao.impl.SubjectDaoImpl;
import by.epamtc.digapply.dao.impl.UserDaoImpl;

/**
 * Thread-safe singleton DAO implementations provider.
 */
public class DaoFactory {
    private final UserDao userDao = new UserDaoImpl();
    private final FacultyDao facultyDao = new FacultyDaoImpl();
    private final SubjectDao subjectDao = new SubjectDaoImpl();

    private DaoFactory() {
    }

    /**
     * Gets instance of {@link DaoFactory}.
     * @return Instance of singleton.
     */
    public static DaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets implementation of {@link UserDao}
     * @return {@link UserDao} implementation.
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Gets implementation of {@link FacultyDao}
     * @return {@link FacultyDao} implementation.
     */
    public FacultyDao getFacultyDao() {
        return facultyDao;
    }

    /**
     * Gets implementation of {@link SubjectDao}
     * @return {@link SubjectDao} implementation.
     */
    public SubjectDao getSubjectDao() { return subjectDao; }

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }
}
