package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Signs user out
 */
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
