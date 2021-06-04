package by.epamtc.digapply.service.factory;

import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService= new UserServiceImpl();

    private static class Holder{
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return Holder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }
}
