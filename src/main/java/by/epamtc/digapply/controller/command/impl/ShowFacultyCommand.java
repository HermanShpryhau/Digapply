package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            Faculty faculty = facultyService.retrieveFacultyById(facultyId);
            if (faculty != null) {
                request.setAttribute(RequestAttribute.FACULTY, faculty);
                ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
                List<ApplicationDto> applications = applicationService.retrieveApplicationsByFaculty(facultyId);
                request.setAttribute(RequestAttribute.APPLICATIONS_COUNT, applications.size());
                List<Subject> subjects = facultyService.retrieveSubjectsForFaculty(faculty);
                request.setAttribute(RequestAttribute.FACULTY_SUBJECTS, subjects);
            } else {
                return Routing.ERROR_404;
            }
        } catch (ServiceException e) {
            logger.error("Unable to retrieve faculty {}. {}", facultyId, e.getMessage());
            return Routing.ERROR_500;
        }

        return new Routing(PagePath.FACULTY_DETAIL_PAGE, RoutingType.FORWARD);
    }
}
