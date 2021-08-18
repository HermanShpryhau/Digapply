package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UpdateApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
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

        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            applicationService.updateApplication(applicationId, scores, certificates);
            return new Routing(PagePath.APPLICATION_TABLE_PAGE_REDIRECT, RoutingType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Unable to update results for application {}. {}", applicationId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
