package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.HOME_COMMAND)
public class HomeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private FacultyService facultyService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
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
