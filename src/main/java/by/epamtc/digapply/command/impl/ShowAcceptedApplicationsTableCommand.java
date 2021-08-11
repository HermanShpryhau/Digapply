package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowAcceptedApplicationsTableCommand implements Command {
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
            return Routing.ERROR_500;
        }
    }
}
