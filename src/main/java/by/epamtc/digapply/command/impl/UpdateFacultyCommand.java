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
    private static final long INVALID_ID = -1;
    private static final int INVALID_PLACES_COUNT = -1;
    private static final String HTML_TAG_SCRIPT = "\\<.*?\\>";

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = parseFacultyId(facultyIdString);
        if (facultyId == INVALID_ID) {
            return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
        }

        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));

        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = parsePlacesCount(placesString);
        if (places == INVALID_PLACES_COUNT) {
            return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
        }

        Optional<String> shortDescription = Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        Faculty updatedFaculty = buildFaculty(facultyId, facultyName, places, shortDescription, facultyDescription);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            if (facultyService.updateFaculty(updatedFaculty)) {
                return new Routing(PagePath.FACULTY_DETAIL_PAGE_REDIRECT + facultyId, RoutingType.REDIRECT);
            } else {
                // TODO Set error data
                return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
    }

    private long parseFacultyId(Optional<String> idString) {
        long id = INVALID_ID;
        if (idString.isPresent()) {
            try {
                id = Long.parseLong(idString.get());
            } catch (NumberFormatException e) {
                id = INVALID_ID;
            }
        }
        return id;
    }

    private int parsePlacesCount(Optional<String> placesCountString) {
        int places = INVALID_PLACES_COUNT;
        if (placesCountString.isPresent()) {
            try {
                places = Integer.parseInt(placesCountString.get());
            } catch (NumberFormatException e) {
                places = INVALID_PLACES_COUNT;
            }
        }
        return places;
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
