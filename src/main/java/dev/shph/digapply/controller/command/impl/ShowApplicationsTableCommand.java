package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.entity.dto.ApplicationDto;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component(CommandName.SHOW_APPLICATIONS_TABLE_COMMAND)
public class ShowApplicationsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private FacultyService facultyService;
    @Autowired
    private ApplicationService applicationService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Faculty> faculties = facultyService.retrieveAllFaculties();
            request.setAttribute(RequestAttribute.FACULTIES, faculties);
        } catch (ServiceException e) {
            logger.error("Unable tp fetch all faculties. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }

        List<ApplicationDto> applications;
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
