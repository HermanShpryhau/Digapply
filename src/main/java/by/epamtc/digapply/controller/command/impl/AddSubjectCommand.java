package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AddSubjectCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> subjectName = Optional.ofNullable(request.getParameter(RequestParameter.SUBJECT_NAME));
        if (!subjectName.isPresent()) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_SUBJECT_DATA);
            return new Routing(PagePath.SUBJECT_FORM_PAGE, RoutingType.REDIRECT);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            if (subjectService.saveSubject(subjectName.get())) {
                return new Routing(PagePath.SUBJECT_TABLE_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_SUBJECT_DATA);
                return new Routing(PagePath.NEW_SUBJECT_FORM_PAGE_REDIRECT, RoutingType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to save subject to DB. {}", e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
