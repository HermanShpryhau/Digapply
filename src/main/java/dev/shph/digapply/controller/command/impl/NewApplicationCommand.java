package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.entity.Subject;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@DiscoverableCommand(CommandName.NEW_APPLICATION_COMMAND)
public class NewApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        HttpSession session = request.getSession();
        try {
            if (applicationService.hasApplication((long) session.getAttribute(SessionAttribute.USER_ID))) {
                return new Forward(PagePath.ONLY_ONE_APPLICATION_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Unable tp check if user already has an application. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            Faculty faculty = facultyService.retrieveFacultyById(facultyId);
            if (faculty.isApplicationClosed()) {
                session.setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.APPLICATION_CLOSED);
                return new Redirect(PagePath.ERROR_PAGE_REDIRECT);
            }
            List<Subject> subjects = facultyService.retrieveSubjectsForFaculty(facultyId);
            request.setAttribute(RequestAttribute.FACULTY, faculty);
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve faculty {} data. {}", facultyId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
        return new Forward(PagePath.APPLICATION_FORM_PAGE);
    }
}
