package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DeleteFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        try {
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            if (facultyService.removeFacultyById(facultyId)) {
                return new Routing(PagePath.FACULTIES_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                return Routing.ERROR_404;
            }
        } catch (NumberFormatException e) {
            return Routing.ERROR_404;
        } catch (ServiceException e) {
            logger.error("unable to delete faculty {} from DB. {}", facultyId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
