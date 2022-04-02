package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.MailService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@DiscoverableCommand(CommandName.APPROVE_APPLICATION_COMMAND)
public class ApproveApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private MailService mailService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Forward(PagePath.ERROR_404_PAGE);
        }
        try {
            if (applicationService.approveApplication(applicationId)) {
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
