package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@DiscoverableCommand(CommandName.HOME_COMMAND)
public class HomeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> bestFaculties;
        try {
            bestFaculties = facultyService.retrieveBestFaculties();
        } catch (ServiceException e) {
            logger.error("Unable to retrieve best faculties. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
        request.setAttribute(RequestAttribute.BEST_FACULTIES, bestFaculties);
        return new Forward(PagePath.HOME_PAGE);
    }
}
