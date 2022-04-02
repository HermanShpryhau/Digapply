package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestAttribute;
import by.epamtc.digapply.controller.command.RequestParameter;
import by.epamtc.digapply.controller.command.RequestParameterParser;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
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

@DiscoverableCommand(CommandName.SHOW_APPLICATIONS_TABLE_COMMAND)
public class ShowApplicationsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            List<Faculty> faculties = facultyService.retrieveAllFaculties();
            request.setAttribute(RequestAttribute.FACULTIES, faculties);
        } catch (ServiceException e) {
            logger.error("Unable tp fetch all faculties. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }

        List<ApplicationDto> applications;
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId != RequestParameterParser.INVALID_POSITIVE_LONG) {
            try {
                applications = applicationService.retrieveApplicationsByFaculty(facultyId);
            } catch (ServiceException e) {
                logger.error("Unable to applications for faculty {}. {}", facultyId, e.getMessage());
                return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
            }
        } else {
            try {
                applications = applicationService.retrieveAllApplicationsDto();
            } catch (ServiceException e) {
                logger.error("Unable to retrieve all applications. {}", e.getMessage());
                return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
            }
        }

        request.setAttribute(RequestAttribute.APPLICATIONS, applications);
        return new Forward(PagePath.APPLICATION_TABLE_PAGE);
    }
}
