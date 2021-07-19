package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.impl.FacultyDaoImpl;
import by.epamtc.digapply.dao.impl.SubjectDaoImpl;
import by.epamtc.digapply.dao.impl.UserDaoImpl;

public class DaoFactory {
    private final UserDao userDao = new UserDaoImpl();
    private final FacultyDao facultyDao = new FacultyDaoImpl();
    private final SubjectDao subjectDao = new SubjectDaoImpl();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public FacultyDao getFacultyDao() {
        return facultyDao;
    }

    public SubjectDao getSubjectDao() { return subjectDao; }

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }
}
