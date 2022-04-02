package dev.shph.digapply.service;

import dev.shph.digapply.service.impl.*;
import dev.shph.digapply.service.impl.ApplicationServiceImpl;
import dev.shph.digapply.service.impl.FacultyServiceImpl;
import dev.shph.digapply.service.impl.MailServiceImpl;
import dev.shph.digapply.service.impl.ResultServiceImpl;
import dev.shph.digapply.service.impl.SubjectServiceImpl;
import dev.shph.digapply.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService = new UserServiceImpl();
    private final FacultyService facultyService = new FacultyServiceImpl();
    private final SubjectService subjectService = new SubjectServiceImpl();
    private final ApplicationService applicationService = new ApplicationServiceImpl();
    private final ResultService resultService = new ResultServiceImpl();
    private final MailService mailService = new MailServiceImpl();

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

    /**
     * Gets implementation of {@link ResultService}
     * @return {@link ResultService} implementation.
     */
    public ResultService getResultService() {
        return resultService;
    }

    /**
     * Gets implementation of {@link MailService}
     * @return {@link MailService} implementation.
     */
    public MailService getMailService() {
        return mailService;
    }

    private static class Holder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }
}
