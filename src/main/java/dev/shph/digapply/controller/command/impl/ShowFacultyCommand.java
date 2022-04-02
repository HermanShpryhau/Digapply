package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.entity.Subject;
import dev.shph.digapply.entity.dto.ApplicationDto;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
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
