package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@DiscoverableCommand(CommandName.SHOW_FACULTY_FORM_COMMAND)
public class ShowFacultyFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId != RequestParameterParser.INVALID_POSITIVE_LONG) {
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            try {
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                request.setAttribute(RequestAttribute.FACULTY, faculty);
            } catch (ServiceException e) {
                logger.error("Unable to retrieve faculty {} data. {}", facultyId, e.getMessage());
                return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
            }
        }

        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            List<Subject> subjects = subjectService.retrieveAllSubjects();
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
            return new Forward(PagePath.FACULTY_FORM_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve all subjects. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
