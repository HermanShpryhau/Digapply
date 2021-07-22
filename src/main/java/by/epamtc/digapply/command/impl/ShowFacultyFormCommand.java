package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowFacultyFormCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdParameter = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        if (facultyIdParameter.isPresent()) {
            long facultyId = Long.parseLong(facultyIdParameter.get());
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            try {
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                request.setAttribute(RequestAttribute.FACULTY, faculty);
            } catch (ServiceException e) {
                return new CommandResult(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
            }
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            List<Subject> subjects = subjectService.retrieveAllSubjects();
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
            return new CommandResult(PagePath.FACULTY_FORM_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
    }
}
