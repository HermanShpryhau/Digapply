package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@DiscoverableCommand(CommandName.DELETE_USER_COMMAND)
public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (userService.deleteUser(userId)) {
                return new Redirect(PagePath.USER_TABLE_PAGE_REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.NO_SUCH_USER);
                return new Redirect(PagePath.ERROR_PAGE_REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Unable to delete user {}. {}", userId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
