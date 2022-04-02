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
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.UPDATE_FACULTY_COMMAND)
public class UpdateFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_REQUEST_PARAM = "&id=";

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        Optional<String> facultyName = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_NAME));

        Optional<String> placesString = Optional.ofNullable(request.getParameter(RequestParameter.PLACES_COUNT));
        int places = RequestParameterParser.parsePositiveInt(placesString);
        if (places == RequestParameterParser.INVALID_POSITIVE_INT) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
            return new Redirect(PagePath.FACULTY_FORM_PAGE_REDIRECT + ID_REQUEST_PARAM + facultyId);
        }

        Optional<String> shortDescription =
                Optional.ofNullable(request.getParameter(RequestParameter.SHORT_FACULTY_DESCRIPTION));
        Optional<String> facultyDescription =
                Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_DESCRIPTION));

        Faculty updatedFaculty = buildFacultyEntity(facultyId, facultyName, places, shortDescription,
                facultyDescription);

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            if (facultyService.updateFaculty(updatedFaculty)) {
                return new Redirect(PagePath.FACULTY_DETAIL_PAGE_REDIRECT + facultyId);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_FACULTY_DATA);
                return new Redirect(PagePath.FACULTY_FORM_PAGE_REDIRECT + ID_REQUEST_PARAM + facultyId);
            }
        } catch (ServiceException e) {
            logger.error("Unable to update faculty {} data. {}", facultyId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }

    private Faculty buildFacultyEntity(long facultyId, Optional<String> facultyName, int places,
                                       Optional<String> shortDescription, Optional<String> facultyDescription) {
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setFacultyId(facultyId);
        updatedFaculty.setFacultyName(facultyName.orElse(null));
        updatedFaculty.setFacultyShortDescription(shortDescription.orElse(null));
        updatedFaculty.setFacultyDescription(facultyDescription.orElse(null));
        updatedFaculty.setPlaces(places);
        return updatedFaculty;
    }
}
