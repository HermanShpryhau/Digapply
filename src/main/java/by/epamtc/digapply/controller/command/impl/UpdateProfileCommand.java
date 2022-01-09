package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.UserRole;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@DiscoverableCommand(CommandName.UPDATE_PROFILE_COMMAND)
public class UpdateProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_REQUEST_PARAM = "&id=";
    private static final String SPACE = " ";

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserService userService = ServiceFactory.getInstance().getUserService();
        long userId;
        boolean hasAdminRights = userService.hasAdminRights((UserRole) session.getAttribute(SessionAttribute.ROLE));
        if (!hasAdminRights) {
            userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
        } else {
            Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
            userId = RequestParameterParser.parsePositiveLong(userIdString);
            if (userId == RequestParameterParser.INVALID_POSITIVE_LONG) {
                return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
            }
        }

        Optional<String> firstName = Optional.ofNullable(request.getParameter(RequestParameter.FIRST_NAME));
        Optional<String> lastName = Optional.ofNullable(request.getParameter(RequestParameter.LAST_NAME));
        if (firstName.isPresent() && lastName.isPresent()) {
            try {
                if (userService.updateUserData(userId, firstName.get(), lastName.get())) {
                    session.setAttribute(SessionAttribute.USERNAME, firstName.get() + SPACE + lastName.get());
                    return routeByRights(hasAdminRights);
                } else {
                    request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_NAME);
                    return new Redirect(PagePath.PROFILE_EDIT_FORM_PAGE_REDIRECT + ID_REQUEST_PARAM + userId);
                }
            } catch (ServiceException e) {
                logger.error("Unable to update user {} data. {}", userId, e.getMessage());
                return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
            }
        } else {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_NAME);
            return new Redirect(PagePath.PROFILE_EDIT_FORM_PAGE_REDIRECT);
        }
    }

    private Routing routeByRights(boolean hasAdminRights) {
        Routing routing;
        if (hasAdminRights) {
            routing = new Redirect(PagePath.USER_TABLE_PAGE_REDIRECT);
        } else {
            routing = new Redirect(PagePath.PROFILE_PAGE_REDIRECT);
        }
        return routing;
    }
}
