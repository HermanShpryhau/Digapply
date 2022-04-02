package dev.shph.digapply.dao;

import dev.shph.digapply.dao.impl.*;
import dev.shph.digapply.dao.impl.ApplicationDaoImpl;
import dev.shph.digapply.dao.impl.FacultyDaoImpl;
import dev.shph.digapply.dao.impl.ResultDaoImpl;
import dev.shph.digapply.dao.impl.SubjectDaoImpl;
import dev.shph.digapply.dao.impl.UserDaoImpl;

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
