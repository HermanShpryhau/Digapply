package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.entity.dto.ApplicationDto;
import dev.shph.digapply.service.ApplicationService;
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

@DiscoverableCommand(CommandName.SHOW_ACCEPTED_APPLICATIONS_TABLE_COMMAND)
public class ShowAcceptedApplicationsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            List<ApplicationDto> applications = applicationService.retrieveAcceptedApplicationsByFaculty(facultyId);
            request.setAttribute(RequestAttribute.APPLICATIONS, applications);
            return new Forward(PagePath.ACCEPTED_APPLICATIONS_TABLE);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve accepted applications for faculty {}. {}", facultyId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
