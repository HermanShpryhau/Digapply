package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.model.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.SHOW_APPLICATION_EDIT_FORM_COMMAND)
public class EditApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            ApplicationDto applicationDto = applicationService.retrieveApplicationDtoById(applicationId);
            request.setAttribute(RequestAttribute.APPLICATION, applicationDto);
            return new Forward(PagePath.APPLICATION_EDIT_FORM_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to fetch DTO for application {}. {}", applicationId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
