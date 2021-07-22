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

public class AddFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final long INVALID_ID = -1L;
    private static final long DEFAULT_ID = 0;
    private static final int INVALID_PLACES_COUNT = -1;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = INVALID_ID;
        if (facultyIdString.isPresent()) {
            try {
                facultyId = facultyIdString.get().equals("") ? DEFAULT_ID :Long.parseLong(facultyIdString.get());
            } catch (NumberFormatException e) {
                // TODO Add error info
                return new CommandResult(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        }
        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));
        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = INVALID_PLACES_COUNT;
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

        List<Long> subjectIds = Arrays.stream(request.getParameterValues(RequestParameter.SUBJECTS))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        Faculty newFaculty = new Faculty();
        newFaculty.setFacultyId(facultyId);
        newFaculty.setFacultyName(facultyName.orElse(null));
        newFaculty.setFacultyShortDescription(shortDescription.orElse(null));
        newFaculty.setFacultyDescription(facultyDescription.orElse(null));
        newFaculty.setPlaces(places);
        newFaculty.setApplicationClosed(false);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            Faculty faculty = facultyService.addFaculty(newFaculty, subjectIds);
            if (faculty != null) {
                return new CommandResult(PagePath.FACULTIES_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                // TODO set error data
                return new CommandResult(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Unable to save faculty.", e);
            return new CommandResult(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
    }
}
