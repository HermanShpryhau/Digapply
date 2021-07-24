package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowSignInFormCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(SessionAttribute.LOGIN_ERROR);
        response.addHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setDateHeader("Expires", 0);
        return new Routing(PagePath.LOGIN_PAGE, RoutingType.FORWARD);
    }
}
