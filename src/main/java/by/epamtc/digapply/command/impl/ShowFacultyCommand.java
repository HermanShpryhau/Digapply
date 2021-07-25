package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Displays details on faculty
 */
public class ShowFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        if (facultyIdString.isPresent()) {
            long facultyId;
            try {
                facultyId = Long.parseLong(facultyIdString.get());
            } catch (NumberFormatException e) {
                return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            try {
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                if (faculty != null) {
                    request.setAttribute(RequestAttribute.FACULTY, faculty);
                    List<Subject> subjects = facultyService.retrieveSubjectsForFaculty(faculty);
                    request.setAttribute(RequestAttribute.FACULTY_SUBJECTS, subjects);
                } else {
                    return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
                }
            } catch (ServiceException e) {
                logger.error("Unable to retrieve faculty", e);
                return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
            }
        }

        return new Routing(PagePath.FACULTY_DETAIL_PAGE, RoutingType.FORWARD);
    }
}
