package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class EditApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            ApplicationDto applicationDto = applicationService.retrieveApplicationDtoById(applicationId);
            request.setAttribute(RequestAttribute.APPLICATION, applicationDto);
            return new Routing(PagePath.APPLICATION_EDIT_FORM_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to fetch DTO for application {}. {}", applicationId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
