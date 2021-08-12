package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowSubjectsTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            List<Subject> subjects = subjectService.retrieveAllSubjects();
            request.setAttribute(RequestAttribute.SUBJECTS, subjects);
            return new Routing(PagePath.SUBJECT_TABLE_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve all subjects. {}", e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
