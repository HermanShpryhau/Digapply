package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.Subject;
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

@DiscoverableCommand(CommandName.SHOW_SUBJECTS_TABLE_COMMAND)
public class ShowSubjectsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            List<Subject> subjects = subjectService.retrieveAllSubjects();
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
            return new Forward(PagePath.SUBJECT_TABLE_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve all subjects. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
