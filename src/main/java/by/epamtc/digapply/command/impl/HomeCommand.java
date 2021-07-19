package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> bestFaculties = null;
        try {
            bestFaculties = facultyService.retrieveBestFaculties();
        } catch (ServiceException e) {
            logger.error("Unable to retrieve best faculties", e);
            return new CommandResult(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
        request.setAttribute(RequestAttribute.BEST_FACULTIES, bestFaculties);
        return new CommandResult(PagePath.HOME_PAGE, RoutingType.FORWARD);
    }
}
