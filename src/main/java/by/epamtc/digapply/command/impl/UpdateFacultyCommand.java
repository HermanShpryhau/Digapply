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
import java.util.Optional;

public class UpdateFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));

        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = RequestParameterParser.parsePositiveInt(placesString);
        if (places == RequestParameterParser.INVALID_POSITIVE_INT) {
            request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
            return Routing.ERROR;
        }

        Optional<String> shortDescription = Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        Faculty updatedFaculty = buildFacultyEntity(facultyId, facultyName, places, shortDescription, facultyDescription);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            if (facultyService.updateFaculty(updatedFaculty)) {
                return new Routing(PagePath.FACULTY_DETAIL_PAGE_REDIRECT + facultyId, RoutingType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
                return Routing.ERROR;
            }
        } catch (ServiceException e) {
            logger.error("Unable to update faculty {} data. {}", facultyId, e.getMessage());
            return Routing.ERROR_500;
        }
    }

    private Faculty buildFacultyEntity(long facultyId, Optional<String> facultyName, int places, Optional<String> shortDescription, Optional<String> facultyDescription) {
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setFacultyId(facultyId);
        updatedFaculty.setFacultyName(facultyName.orElse(null));
        updatedFaculty.setFacultyShortDescription(shortDescription.orElse(null));
        updatedFaculty.setFacultyDescription(facultyDescription.orElse(null));
        updatedFaculty.setPlaces(places);
        return updatedFaculty;
    }
}
