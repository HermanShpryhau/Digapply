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
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.EDIT_SUBJECT_COMMAND)
public class EditSubjectCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> subjectIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long subjectId = RequestParameterParser.parsePositiveLong(subjectIdString);
        if (subjectId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            Subject subject = subjectService.retrieveSubjectById(subjectId);
            request.setAttribute(RequestAttribute.SUBJECT, subject);
            return new Forward(PagePath.SUBJECT_FORM_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve subject {} from DB. {}", subjectId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
