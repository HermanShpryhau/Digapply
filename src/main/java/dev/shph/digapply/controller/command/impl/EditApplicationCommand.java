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
import dev.shph.digapply.entity.dto.ApplicationDto;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component(CommandName.SHOW_APPLICATION_EDIT_FORM_COMMAND)
public class EditApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ApplicationService applicationService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
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
