package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DeleteFacultyCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        if (facultyIdString.isPresent()) {
            try {
                long facultyId = Long.parseLong(facultyIdString.get());
                FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
                if (facultyService.removeFacultyById(facultyId)) {
                    return new Routing(PagePath.FACULTIES_PAGE_REDIRECT, RoutingType.REDIRECT);
                } else {
                    return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
                }
            } catch (NumberFormatException e) {
                // TODO set error data
                return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            } catch (ServiceException e) {
                return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
            }
        } else {
            return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
        }
    }
}
