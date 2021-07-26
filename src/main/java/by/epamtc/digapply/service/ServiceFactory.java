package by.epamtc.digapply.service;

import by.epamtc.digapply.service.impl.ApplicationServiceImpl;
import by.epamtc.digapply.service.impl.FacultyServiceImpl;
import by.epamtc.digapply.service.impl.SubjectServiceImpl;
import by.epamtc.digapply.service.impl.UserServiceImpl;

/**
 * Thread-safe singleton Service implementations provider.
 */
public class ServiceFactory {
    private final UserService userService = new UserServiceImpl();
    private final FacultyService facultyService = new FacultyServiceImpl();
    private final SubjectService subjectService = new SubjectServiceImpl();
    private final ApplicationService applicationService = new ApplicationServiceImpl();

    private ServiceFactory() {
    }

    /**
     * Gets instance of {@link ServiceFactory}.
     * @return Instance of singleton.
     */
    public static ServiceFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets implementation of {@link UserService}
     * @return {@link UserService} implementation.
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Gets implementation of {@link FacultyService}
     * @return {@link FacultyService} implementation.
     */
    public FacultyService getFacultyService() {
        return facultyService;
    }

    /**
     * Gets implementation of {@link SubjectService}
     * @return {@link SubjectService} implementation.
     */
    public SubjectService getSubjectService() {
        return subjectService;
    }

    /**
     * Gets implementation of {@link ApplicationService}
     * @return {@link ApplicationService} implementation.
     */
    public ApplicationService getApplicationService() {
        return applicationService;
    }

    private static class Holder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }
}
