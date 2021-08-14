package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.impl.*;

public class DaoFactory {
    private final UserDao userDao = new UserDaoImpl();
    private final FacultyDao facultyDao = new FacultyDaoImpl();
    private final SubjectDao subjectDao = new SubjectDaoImpl();
    private final ApplicationDao applicationDao = new ApplicationDaoImpl();
    private final ResultDao resultDao = new ResultDaoImpl();

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

    public ApplicationDao getApplicationDao() {
        return applicationDao;
    }

    public ResultDao getResultDao() {
        return resultDao;
    }

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }
}
