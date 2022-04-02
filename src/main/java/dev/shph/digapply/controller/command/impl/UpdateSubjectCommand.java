package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.Subject;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.SubjectService;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.UPDATE_SUBJECT_COMMAND)
public class UpdateSubjectCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_REQUEST_PARAM = "&id=";

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> subjectIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long subjectId = RequestParameterParser.parsePositiveLong(subjectIdString);
        if (subjectId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        Optional<String> subjectName = Optional.ofNullable(request.getParameter(RequestParameter.SUBJECT_NAME));
        if (!subjectName.isPresent()) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_SUBJECT_DATA);
            return new Redirect(PagePath.EDIT_SUBJECT_FORM_PAGE_REDIRECT + ID_REQUEST_PARAM + subjectId);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            if (subjectService.updateSubject(new Subject(subjectId, subjectName.get()))) {
                return new Redirect(PagePath.SUBJECT_TABLE_PAGE_REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_SUBJECT_DATA);
                return new Redirect(PagePath.EDIT_SUBJECT_FORM_PAGE_REDIRECT + ID_REQUEST_PARAM + subjectId);
            }
        } catch (ServiceException e) {
            logger.error("Unable to update subject {} data. {}", subjectId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
