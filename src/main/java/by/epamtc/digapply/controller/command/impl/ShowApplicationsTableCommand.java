package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowApplicationsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            List<Faculty> faculties = facultyService.retrieveAllFaculties();
            request.setAttribute(RequestAttribute.FACULTIES, faculties);
        } catch (ServiceException e) {
            logger.error("Unable tp fetch all faculties. {}", e.getMessage());
            return Routing.ERROR_500;
        }

        List<ApplicationDto> applications;
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId != RequestParameterParser.INVALID_POSITIVE_LONG) {
            try {
                applications = applicationService.retrieveApplicationsByFaculty(facultyId);
            } catch (ServiceException e) {
                logger.error("Unable to applications for faculty {}. {}", facultyId, e.getMessage());
                return Routing.ERROR_500;
            }
        } else {
            try {
                applications = applicationService.retrieveAllApplicationsDto();
            } catch (ServiceException e) {
                logger.error("Unable to retrieve all applications. {}", e.getMessage());
                return Routing.ERROR_500;
            }
        }

        request.setAttribute(RequestAttribute.APPLICATIONS, applications);
        return new Routing(PagePath.APPLICATION_TABLE_PAGE, RoutingType.FORWARD);
    }
}
