package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowErrorPageCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String errorKey = (String) session.getAttribute(SessionAttribute.ERROR_KEY);
        session.removeAttribute(SessionAttribute.ERROR_KEY);
        request.setAttribute(RequestAttribute.ERROR_KEY, errorKey);
        return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
    }
}
