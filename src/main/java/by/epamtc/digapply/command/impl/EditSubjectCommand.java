package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class EditSubjectCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> subjectIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long subjectId = RequestParameterParser.parsePositiveLong(subjectIdString);
        if (subjectId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            Subject subject = subjectService.retrieveSubjectById(subjectId);
            request.setAttribute(RequestAttribute.SUBJECT, subject);
            return new Routing(PagePath.SUBJECT_FORM_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
    }
}
