package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Updates faculty data in datasource
 */
public class UpdateFacultyCommand implements Command {
    private static final String HTML_TAG_SCRIPT = "\\<.*?\\>";

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            // TODO Set error data
            return Routing.ERROR;
        }

        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));

        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = RequestParameterParser.parsePositiveInt(placesString);
        if (places == RequestParameterParser.INVALID_POSITIVE_INT) {
            // TODO Set error data
            return Routing.ERROR;
        }

        Optional<String> shortDescription = Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        Faculty updatedFaculty = buildFaculty(facultyId, facultyName, places, shortDescription, facultyDescription);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            if (facultyService.updateFaculty(updatedFaculty)) {
                return new Routing(PagePath.FACULTY_DETAIL_PAGE_REDIRECT + facultyId, RoutingType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
                return Routing.ERROR;
            }
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
    }

    private Faculty buildFaculty(long facultyId, Optional<String> facultyName, int places, Optional<String> shortDescription, Optional<String> facultyDescription) {
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setFacultyId(facultyId);
        updatedFaculty.setFacultyName(facultyName.orElse(null));
        updatedFaculty.setFacultyShortDescription(shortDescription.orElse(null));
        updatedFaculty.setFacultyDescription(sanitizeString(facultyDescription));
        updatedFaculty.setPlaces(places);
        return updatedFaculty;
    }

    private String sanitizeString(Optional<String> stringOptional) {
        String sanitizedString = null;
        if (stringOptional.isPresent()) {
            sanitizedString = stringOptional.get().replaceAll(HTML_TAG_SCRIPT, "");
        }
        return sanitizedString;
    }
}
