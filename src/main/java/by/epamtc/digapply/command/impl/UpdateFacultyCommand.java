package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UpdateFacultyCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = -1L;
        if (facultyIdString.isPresent()) {
            try {
                facultyId = Long.parseLong(facultyIdString.get());
            } catch (NumberFormatException e) {
                // TODO Add error info
                return new CommandResult(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        }
        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));
        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = -1;
        if (placesString.isPresent()) {
            try {
                places = Integer.parseInt(placesString.get());
            } catch (NumberFormatException e) {
                // TODO Add error info
                return new CommandResult(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        }
        Optional<String> shortDescription = Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setFacultyId(facultyId);
        updatedFaculty.setFacultyName(facultyName.orElse(null));
        updatedFaculty.setFacultyShortDescription(shortDescription.orElse(null));
        updatedFaculty.setFacultyDescription(facultyDescription.orElse(null));
        updatedFaculty.setPlaces(places);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            if (facultyService.updateFaculty(updatedFaculty)) {
                return new CommandResult(PagePath.FACULTY_DETAIL_PAGE_REDIRECT + facultyId, RoutingType.REDIRECT);
            } else {
                // TODO Set error data
                return new CommandResult(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
    }
}
