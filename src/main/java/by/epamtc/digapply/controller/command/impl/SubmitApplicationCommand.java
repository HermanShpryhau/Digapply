package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.ApplicationService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@DiscoverableCommand(CommandName.SUBMIT_APPLICATION_COMMAND)
public class SubmitApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, String> scores = new HashMap<>();
        Map<String, String> certificates = new HashMap<>();
        for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
            if (parameter.getKey().startsWith(RequestParameter.SUBJECT_ID_PREFIX)) {
                scores.put(parameter.getKey(), parameter.getValue()[0]);
            } else if (parameter.getKey().startsWith(RequestParameter.CERTIFICATE_ID_PREFIX)) {
                certificates.put(parameter.getKey(), parameter.getValue()[0]);
            }
        }

        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            long userId = (long) request.getSession().getAttribute(SessionAttribute.USER_ID);
            if (applicationService.saveApplication(userId, facultyId, scores, certificates)) {
                return new Redirect(PagePath.PROFILE_PAGE_REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_APPLICATION_DATA);
                return new Redirect(PagePath.APPLICATION_FORM_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to save application to DB. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
