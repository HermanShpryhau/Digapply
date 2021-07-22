package by.epamtc.digapply.service;

import by.epamtc.digapply.service.impl.FacultyServiceImpl;
import by.epamtc.digapply.service.impl.SubjectServiceImpl;
import by.epamtc.digapply.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService = new UserServiceImpl();
    private final FacultyService facultyService = new FacultyServiceImpl();
    private final SubjectService subjectService = new SubjectServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return Holder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public FacultyService getFacultyService() {
        return facultyService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    private static class Holder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }
}
