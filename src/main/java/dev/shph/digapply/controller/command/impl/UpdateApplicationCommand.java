package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@DiscoverableCommand(CommandName.UPDATE_APPLICATION_COMMAND)
public class UpdateApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_REQUEST_PARAM = "&id=";

    @Autowired
    private ApplicationService applicationService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, String> scores = new HashMap<>();
        Map<String, String> certificates = new HashMap<>();
        parameters.entrySet().stream()
                .filter(e -> e.getKey().startsWith(RequestParameter.SUBJECT_ID_PREFIX))
                .forEach(e -> scores.put(e.getKey(), e.getValue()[0]));
        parameters.entrySet().stream()
                .filter(e -> e.getKey().startsWith(RequestParameter.CERTIFICATE_ID_PREFIX))
                .forEach(e -> certificates.put(e.getKey(), e.getValue()[0]));

        try {
            if (applicationService.updateApplication(applicationId, scores, certificates)) {
                return new Redirect(PagePath.APPLICATION_TABLE_PAGE_REDIRECT);
            } else {
                return new Redirect(PagePath.APPLICATION_EDIT_FORM_PAGE_REDIRECT + ID_REQUEST_PARAM + applicationId);
            }
        } catch (ServiceException e) {
            logger.error("Unable to update results for application {}. {}", applicationId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
