package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UpdatePasswordCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));
        if (password.isPresent()) {
            UserService userService = ServiceFactory.getInstance().getUserService();
            HttpSession session = request.getSession();
            try {
                if (userService.updatePassword((Long) session.getAttribute(SessionAttribute.USER_ID), password.get())) {
                    session.invalidate();
                    return new Routing(PagePath.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
                } else {
                    // TODO Set error data - invalid password.
                    return Routing.ERROR;
                }
            } catch (ServiceException e) {
                return Routing.ERROR_500;
            }
        }
        // TODO Set error data - invalid password.
        return Routing.ERROR;
    }
}
