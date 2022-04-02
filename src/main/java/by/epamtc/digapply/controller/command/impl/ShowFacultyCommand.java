package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.Subject;
import by.epamtc.digapply.model.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@DiscoverableCommand(CommandName.SHOW_FACULTY_COMMAND)
public class ShowFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
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
                return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to retrieve faculty {}. {}", facultyId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }

        return new Forward(PagePath.FACULTY_DETAIL_PAGE);
    }
}
