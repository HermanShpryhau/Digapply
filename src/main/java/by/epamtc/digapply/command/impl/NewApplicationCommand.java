package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class NewApplicationCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
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
                return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
            } catch (ServiceException e) {
                return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
            }
        } else {
            return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
        }
        return new Routing(PagePath.APPLICATION_FORM, RoutingType.FORWARD);
    }
}
