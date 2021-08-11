package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class CloseApplicationCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            List<ApplicationDto> acceptedApplications = facultyService.closeApplication(facultyId);
            if (acceptedApplications == null) {
                // TODO Set error data - no such faulty
                return Routing.ERROR;
            }
            request.setAttribute(RequestAttribute.APPLICATIONS, acceptedApplications);
            return new Routing(PagePath.ACCEPTED_APPLICATIONS_TABLE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
    }
}
