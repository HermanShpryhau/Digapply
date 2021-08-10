package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ShowProfileEditFormCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserService userService = ServiceFactory.getInstance().getUserService();
        if (!userService.hasAdminRights((Long) session.getAttribute(SessionAttribute.ROLE))) {
            request.setAttribute(RequestAttribute.ID, session.getAttribute(SessionAttribute.USER_ID));
            return new Routing(PagePath.PROFILE_EDIT_FORM_PAGE, RoutingType.FORWARD);
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
            return Routing.ERROR_500;
        }
    }
}
