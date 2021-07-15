package by.epamtc.digapply.command;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.resource.RequestAttribute;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> bestFaculties = facultyService.retrieveBestFaculties();
        request.setAttribute(RequestAttribute.BEST_FACULTIES, bestFaculties);
        return new CommandResult(Page.HOME_PAGE, RoutingType.FORWARD);
    }
}
