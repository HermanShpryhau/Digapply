package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.ErrorKey;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestParameter;
import by.epamtc.digapply.controller.command.SessionAttribute;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.ADD_SUBJECT_COMMAND)
public class AddSubjectCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> subjectName = Optional.ofNullable(request.getParameter(RequestParameter.SUBJECT_NAME));
        if (!subjectName.isPresent()) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_SUBJECT_DATA);
            return new Redirect(PagePath.SUBJECT_FORM_PAGE);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            if (subjectService.saveSubject(subjectName.get())) {
                return new Redirect(PagePath.SUBJECT_TABLE_PAGE_REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_SUBJECT_DATA);
                return new Redirect(PagePath.NEW_SUBJECT_FORM_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to save subject to DB. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
