package by.epamtc.digapply.dao;

import by.epamtc.digapply.dao.mysql.SqlUserDao;

public class DaoFactory {
    private final UserDao userDao = new SqlUserDao();

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
