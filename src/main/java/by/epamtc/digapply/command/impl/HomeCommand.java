package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> bestFaculties;
        try {
            bestFaculties = facultyService.retrieveBestFaculties();
        } catch (ServiceException e) {
            logger.error("Unable to retrieve best faculties. {}", e.getMessage());
            return Routing.ERROR_500;
        }
        request.setAttribute(RequestAttribute.BEST_FACULTIES, bestFaculties);
        return new Routing(PagePath.HOME_PAGE, RoutingType.FORWARD);
    }
}
