package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class NewApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        HttpSession session = request.getSession();
        try {
            if (applicationService.hasApplication((long) session.getAttribute(SessionAttribute.USER_ID))) {
                return new Routing(PagePath.ONLY_ONE_APPLICATION_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Unable tp check if user already has an application. {}", e.getMessage());
            return Routing.ERROR_500;
        }
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId =RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            Faculty faculty = facultyService.retrieveFacultyById(facultyId);
            List<Subject> subjects = facultyService.retrieveSubjectsForFaculty(facultyId);
            request.setAttribute(RequestAttribute.FACULTY, faculty);
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve faculty {} data. {}", facultyId, e.getMessage());
            return Routing.ERROR_500;
        }
        return new Routing(PagePath.APPLICATION_FORM_PAGE, RoutingType.FORWARD);
    }
}
