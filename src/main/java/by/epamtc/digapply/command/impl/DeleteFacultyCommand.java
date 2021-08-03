package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Deletes faculty form data source
 */
public class DeleteFacultyCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        if (facultyIdString.isPresent()) {
            try {
                long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
                FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
                if (facultyService.removeFacultyById(facultyId)) {
                    return new Routing(PagePath.FACULTIES_PAGE_REDIRECT, RoutingType.REDIRECT);
                } else {
                    return Routing.ERROR_404;
                }
            } catch (NumberFormatException e) {
                return Routing.ERROR_404;
            } catch (ServiceException e) {
                return Routing.ERROR_500;
            }
        } else {
            return Routing.ERROR_404;
        }
    }
}
