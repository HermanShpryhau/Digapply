package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UpdateProfileCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserService userService = ServiceFactory.getInstance().getUserService();
        long userId;
        boolean hasAdminRights = userService.hasAdminRights((Long) session.getAttribute(SessionAttribute.ROLE));
        if (!hasAdminRights) {
            userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
        } else {
            Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
            userId = RequestParameterParser.parsePositiveLong(userIdString);
            if (userId == RequestParameterParser.INVALID_POSITIVE_LONG) {
                return Routing.ERROR_404;
            }
        }

        Optional<String> firstName = Optional.ofNullable(request.getParameter(RequestParameter.FIRST_NAME));
        Optional<String> lastName = Optional.ofNullable(request.getParameter(RequestParameter.LAST_NAME));
        if (firstName.isPresent() && lastName.isPresent()) {
            try {
                if (userService.updateUserData(userId, firstName.get(), lastName.get())) {
                    return routeByRights(hasAdminRights);
                } else {
                    // TODO Set error data - invalid name, surname.
                    return Routing.ERROR;
                }
            } catch (ServiceException e) {
                return Routing.ERROR_500;
            }
        } else {
            // TODO Set error data - invalid name, surname.
            return Routing.ERROR;
        }
    }

    private Routing routeByRights(boolean hasAdminRights) {
        Routing routing;
        if (hasAdminRights) {
            routing = new Routing(PagePath.USER_TABLE_PAGE_REDIRECT, RoutingType.REDIRECT);
        } else {
            routing = new Routing(PagePath.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
        }
        return routing;
    }
}
