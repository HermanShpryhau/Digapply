package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.USERNAME);
        session.removeAttribute(SessionAttribute.ROLE);
        session.removeAttribute(SessionAttribute.USER_ID);
        session.invalidate();
        return new Routing(PagePath.HOME_PAGE_REDIRECT, RoutingType.REDIRECT);
    }
}
