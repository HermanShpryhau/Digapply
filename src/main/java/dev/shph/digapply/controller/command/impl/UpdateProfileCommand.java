package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.RequestParameterParser;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component(CommandName.UPDATE_PROFILE_COMMAND)
public class UpdateProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_REQUEST_PARAM = "&id=";
    private static final String SPACE = " ";

    @Autowired
    private UserService userService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
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
