package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.DELETE_FACULTY_COMMAND)
public class DeleteFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        try {
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            if (facultyService.removeFacultyById(facultyId)) {
                return new Redirect(PagePath.FACULTIES_PAGE_REDIRECT);
            } else {
                return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
            }
        } catch (NumberFormatException e) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        } catch (ServiceException e) {
            logger.error("unable to delete faculty {} from DB. {}", facultyId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
