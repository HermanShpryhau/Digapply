package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.entity.UserRole;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ShowProfileEditFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (!userService.hasAdminRights((UserRole) session.getAttribute(SessionAttribute.ROLE))) {
            User user = null;
            long sessionUserId = (long)session.getAttribute(SessionAttribute.USER_ID);
            try {
                user = userService.retrieveUserById(sessionUserId);
                request.setAttribute(RequestAttribute.USER, user);
                return new Routing(PagePath.PROFILE_EDIT_FORM_PAGE, RoutingType.FORWARD);
            } catch (ServiceException e) {
                logger.error("Unable to retrieve user {} data. {}", sessionUserId, e.getMessage());
                return Routing.ERROR_500;
            }
        }

        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }
        request.setAttribute(RequestAttribute.ID, userId);

        try {
            User user = userService.retrieveUserById(userId);
            request.setAttribute(RequestAttribute.USER, user);
            return new Routing(PagePath.PROFILE_EDIT_FORM_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve user {} data. {}", userId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
