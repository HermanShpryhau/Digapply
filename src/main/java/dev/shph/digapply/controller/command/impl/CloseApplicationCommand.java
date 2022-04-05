package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.dto.ApplicationDto;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.MailService;
import dev.shph.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component(CommandName.CLOSE_APPLICATION_COMMAND)
public class CloseApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private FacultyService facultyService;
    @Autowired
    private MailService mailService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }

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
        for (ApplicationDto application : applications) {
            mailService.sendApplicationAcceptedMessage(application.getApplicationId(), locale);
        }
    }
}
