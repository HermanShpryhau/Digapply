package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class NewApplicationCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        HttpSession session = request.getSession();
        try {
            if (applicationService.hasApplication((long) session.getAttribute(SessionAttribute.USER_ID))) {
                return new Routing(PagePath.ONLY_ONE_APPLICATION_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        if (facultyIdString.isPresent()) {
            try {
                long facultyId = Long.parseLong(facultyIdString.get());
                FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                List<Subject> subjects = facultyService.retrieveSubjectsForFaculty(facultyId);
                request.setAttribute(RequestAttribute.FACULTY, faculty);
                request.setAttribute(RequestAttribute.SUBJECTS, subjects);
            } catch (NumberFormatException e) {
                return Routing.ERROR_404;
            } catch (ServiceException e) {
                return Routing.ERROR_500;
            }
        } else {
            return Routing.ERROR_404;
        }
        return new Routing(PagePath.APPLICATION_FORM_PAGE, RoutingType.FORWARD);
    }
}
