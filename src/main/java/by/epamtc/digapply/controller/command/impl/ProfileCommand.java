package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            Application application = applicationService.retrieveApplicationByUserId((long)session.getAttribute(SessionAttribute.USER_ID));
            if (application != null) {
                request.setAttribute(RequestAttribute.APPLICATION, application);
                FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
                Faculty faculty = facultyService.retrieveFacultyById(application.getFacultyId());
                request.setAttribute(RequestAttribute.FACULTY_NAME, faculty.getFacultyName());
                int totalScore = applicationService.calculateTotalScore(application.getId());
                request.setAttribute(RequestAttribute.TOTAL_SCORE, totalScore);
            }
        } catch (ServiceException e) {
            logger.error("Unable to fetch application data. {}", e.getMessage());
            return Routing.ERROR_500;
        }
        return new Routing(PagePath.PROFILE_PAGE, RoutingType.FORWARD);
    }
}