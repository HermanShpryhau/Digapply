package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DiscoverableCommand(CommandName.ADD_FACULTY_COMMAND)
public class AddFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final long NEW_FACULTY_ID = 0;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));
        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = RequestParameterParser.parsePositiveInt(placesString);
        if (places == RequestParameterParser.INVALID_POSITIVE_INT) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_PLACES_COUNT);
            return new Redirect(PagePath.FACULTY_FORM_PAGE_REDIRECT);
        }
        Optional<String> shortDescription =
                Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription =
                Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        List<Long> subjectIds = buildSubjectIdsList(request);
        Faculty newFaculty = buildFaculty(facultyName, places, shortDescription, facultyDescription);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            Faculty faculty = facultyService.saveFaculty(newFaculty, subjectIds);
            if (faculty != null) {
                return new Redirect(PagePath.FACULTIES_PAGE_REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
                return new Redirect(PagePath.FACULTY_FORM_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to save faculty. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
    }

    private List<Long> buildSubjectIdsList(HttpServletRequest request) {
        return Arrays.stream(request.getParameterValues(RequestParameter.SUBJECTS))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    private Faculty buildFaculty(Optional<String> facultyName, int places, Optional<String> shortDescription,
                                 Optional<String> facultyDescription) {
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
