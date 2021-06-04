package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.mysql.SqlUserDao;

public class DaoFactory {
    private final UserDao USER_DAO = new SqlUserDao();

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }

    private DaoFactory() {}

    private static DaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    public UserDao getUserDao() {
        return USER_DAO;
    }
}
