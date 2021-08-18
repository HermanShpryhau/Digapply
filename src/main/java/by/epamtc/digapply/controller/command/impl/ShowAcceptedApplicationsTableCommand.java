package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowAcceptedApplicationsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            List<ApplicationDto> applications = applicationService.retrieveAcceptedApplicationsByFaculty(facultyId);
            request.setAttribute(RequestAttribute.APPLICATIONS, applications);
            return new Routing(PagePath.ACCEPTED_APPLICATIONS_TABLE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve accepted applications for faculty {}. {}", facultyId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
