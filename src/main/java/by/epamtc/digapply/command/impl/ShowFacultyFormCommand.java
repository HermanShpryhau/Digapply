package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowFacultyFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId != RequestParameterParser.INVALID_POSITIVE_LONG) {
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            try {
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                request.setAttribute(RequestAttribute.FACULTY, faculty);
            } catch (ServiceException e) {
                logger.error("Unable to retrieve faculty {} data. {}", facultyId, e.getMessage());
                return Routing.ERROR_500;
            }
        }

        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            List<Subject> subjects = subjectService.retrieveAllSubjects();
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
            return new Routing(PagePath.FACULTY_FORM_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve all subjects. {}", e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
