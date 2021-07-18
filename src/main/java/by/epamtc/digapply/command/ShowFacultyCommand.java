package by.epamtc.digapply.command;

import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.resource.RequestAttribute;
import by.epamtc.digapply.resource.RequestParameter;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ShowFacultyCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        if (facultyIdString.isPresent()) {
            long facultyId;
            try {
                facultyId = Long.parseLong(facultyIdString.get());
            } catch (NumberFormatException e) {
                throw new ServiceException("Wrong ID format.", e);
            }
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            try {
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                if (faculty != null) {
                    request.setAttribute(RequestAttribute.FACULTY, faculty);
                } else {
                    // TODO Set error parameter
                    return new CommandResult(Page.ERROR_PAGE, RoutingType.FORWARD);
                }
            } catch (ServiceException e) {
                throw new ServiceException("Unable to retrieve faculty");
            }
        }

        return new CommandResult(Page.FACULTY_DETAIL_PAGE, RoutingType.FORWARD);
    }
}
