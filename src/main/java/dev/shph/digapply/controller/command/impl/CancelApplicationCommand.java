package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component(CommandName.CANCEL_APPLICATION_COMMAND)
public class CancelApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ApplicationService applicationService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.USER_ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_INT) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        try {
            if (applicationService.cancelApplication(userId)) {
                if (request.getSession().getAttribute(SessionAttribute.ROLE) == UserRole.ADMIN) {
                    return new Redirect(PagePath.APPLICATION_TABLE_PAGE_REDIRECT);
                }
                return new Redirect(PagePath.PROFILE_PAGE_REDIRECT);
            } else {
                return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to cancel application of user-{}. {}", userId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
