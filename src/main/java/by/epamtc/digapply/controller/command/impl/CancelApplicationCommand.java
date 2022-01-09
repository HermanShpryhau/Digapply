package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.UserRole;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.CANCEL_APPLICATION_COMMAND)
public class CancelApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.USER_ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_INT) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
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
