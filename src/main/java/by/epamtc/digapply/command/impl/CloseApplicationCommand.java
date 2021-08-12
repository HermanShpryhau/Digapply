package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.MailService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class CloseApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            List<ApplicationDto> acceptedApplications = facultyService.closeApplication(facultyId);
            if (acceptedApplications == null) {
                request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.NO_SUCH_FACULTY);
                return Routing.ERROR;
            }
            String locale = (String) request.getSession().getAttribute(SessionAttribute.LOCALE);
            sendAcceptanceEmails(acceptedApplications, locale);
            request.setAttribute(RequestAttribute.APPLICATIONS, acceptedApplications);
            return new Routing(PagePath.ACCEPTED_APPLICATIONS_TABLE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to close application for faculty {}. {}", facultyId, e.getMessage());
            return Routing.ERROR_500;
        }
    }

    void sendAcceptanceEmails(List<ApplicationDto> applications, String locale) throws ServiceException {
        MailService mailService = ServiceFactory.getInstance().getMailService();
        for (ApplicationDto application : applications) {
            mailService.sendApplicationAcceptedMessage(application.getApplicationId(), locale);
        }
    }
}
