package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.ServiceFactory;
import dev.shph.digapply.service.UserService;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@DiscoverableCommand(CommandName.SHOW_PROFILE_EDIT_FORM_COMMAND)
public class ShowProfileEditFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (!userService.hasAdminRights((UserRole) session.getAttribute(SessionAttribute.ROLE))) {
            User user = null;
            long sessionUserId = (long) session.getAttribute(SessionAttribute.USER_ID);
            try {
                user = userService.retrieveUserById(sessionUserId);
                request.setAttribute(RequestAttribute.USER, user);
                return new Forward(PagePath.PROFILE_EDIT_FORM_PAGE);
            } catch (ServiceException e) {
                logger.error("Unable to retrieve user {} data. {}", sessionUserId, e.getMessage());
                return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
            }
        }

        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
        }
        request.setAttribute(RequestAttribute.ID, userId);

        try {
            User user = userService.retrieveUserById(userId);
            request.setAttribute(RequestAttribute.USER, user);
            return new Forward(PagePath.PROFILE_EDIT_FORM_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve user {} data. {}", userId, e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
