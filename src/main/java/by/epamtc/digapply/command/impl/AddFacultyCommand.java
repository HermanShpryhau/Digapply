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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Saves faculty to data source
 */
public class AddFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final long NEW_FACULTY_ID = 0;

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));
        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = RequestParameterParser.parsePositiveInt(placesString);
        if (places == RequestParameterParser.INVALID_POSITIVE_INT) {
            request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.INVALID_PLACES_COUNT);
            return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
        }
        Optional<String> shortDescription = Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        List<Long> subjectIds = buildSubjectIdsList(request);
        Faculty newFaculty = buildFaculty(facultyName, places, shortDescription, facultyDescription);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            Faculty faculty = facultyService.saveFaculty(newFaculty, subjectIds);
            if (faculty != null) {
                return new Routing(PagePath.FACULTIES_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
                return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Unable to save faculty.", e);
            return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
    }

    private List<Long> buildSubjectIdsList(HttpServletRequest request) {
        return Arrays.stream(request.getParameterValues(RequestParameter.SUBJECTS))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    private Faculty buildFaculty(Optional<String> facultyName, int places, Optional<String> shortDescription, Optional<String> facultyDescription) {
        Faculty newFaculty = new Faculty();
        newFaculty.setFacultyId(NEW_FACULTY_ID);
        newFaculty.setFacultyName(facultyName.orElse(null));
        newFaculty.setFacultyShortDescription(shortDescription.orElse(null));
        newFaculty.setFacultyDescription(facultyDescription.orElse(null));
        newFaculty.setPlaces(places);
        newFaculty.setApplicationClosed(false);
        return newFaculty;
    }
}
