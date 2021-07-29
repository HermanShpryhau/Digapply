package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Displays user profile
 */
public class ProfileCommand implements Command {
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
            return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
        return new Routing(PagePath.PROFILE_PAGE, RoutingType.FORWARD);
    }
}
