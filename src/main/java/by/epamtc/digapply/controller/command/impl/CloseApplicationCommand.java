package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.ErrorKey;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestAttribute;
import by.epamtc.digapply.controller.command.RequestParameter;
import by.epamtc.digapply.controller.command.RequestParameterParser;
import by.epamtc.digapply.controller.command.SessionAttribute;
import by.epamtc.digapply.entity.dto.ApplicationDto;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.MailService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@DiscoverableCommand(CommandName.CLOSE_APPLICATION_COMMAND)
public class CloseApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        try {
            List<ApplicationDto> acceptedApplications = facultyService.closeApplication(facultyId);
            if (acceptedApplications == null) {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.NO_SUCH_FACULTY);
                return new Redirect(PagePath.ERROR_PAGE_REDIRECT);
            }
            String locale = (String) request.getSession().getAttribute(SessionAttribute.LOCALE);
            sendAcceptanceEmails(acceptedApplications, locale);
            request.setAttribute(RequestAttribute.APPLICATIONS, acceptedApplications);
            return new Forward(PagePath.ACCEPTED_APPLICATIONS_TABLE);
        } catch (ServiceException e) {
            logger.error("Unable to close application for faculty {}. {}", facultyId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }

    void sendAcceptanceEmails(List<ApplicationDto> applications, String locale) throws ServiceException {
        MailService mailService = ServiceFactory.getInstance().getMailService();
        for (ApplicationDto application : applications) {
            mailService.sendApplicationAcceptedMessage(application.getApplicationId(), locale);
        }
    }
}
