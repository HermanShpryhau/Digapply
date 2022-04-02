package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestParameter;
import by.epamtc.digapply.controller.command.RequestParameterParser;
import by.epamtc.digapply.controller.command.SessionAttribute;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.MailService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.APPROVE_APPLICATION_COMMAND)
public class ApproveApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Forward(PagePath.ERROR_404_PAGE);
        }
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            if (applicationService.approveApplication(applicationId)) {
                MailService mailService = ServiceFactory.getInstance().getMailService();
                String sessionLocale = (String) request.getSession().getAttribute(SessionAttribute.LOCALE);
                mailService.sendApplicationApprovalMessage(applicationId, sessionLocale);
                return new Redirect(PagePath.APPLICATION_TABLE_PAGE_REDIRECT);
            } else {
                return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("unable tp approve application id: {}. {}", applicationId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
