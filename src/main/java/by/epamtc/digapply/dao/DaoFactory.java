package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.impl.*;

public class DaoFactory {
    private final UserDao userDao = new UserDaoImpl();
    private final FacultyDao facultyDao = new FacultyDaoImpl();
    private final SubjectDao subjectDao = new SubjectDaoImpl();
    private final ApplicationDao applicationDao = new ApplicationDaoImpl();
    private final ResultDao resultDao = new ResultDaoImpl();
    private final RoleDao roleDao = new RoleDaoImpl();

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

    /**
     * Gets implementation of {@link ApplicationDao}
     * @return {@link ApplicationDao} implementation.
     */
    public ApplicationDao getApplicationDao() {
        return applicationDao;
    }

    /**
     * Gets implementation of {@link ResultDao}
     * @return {@link ResultDao} implementation.
     */
    public ResultDao getResultDao() {
        return resultDao;
    }

    /**
     * Gets implementation of {@link ResultDao}
     * @return {@link RoleDao} implementation.
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }
}
