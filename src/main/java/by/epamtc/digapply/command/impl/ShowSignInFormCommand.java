package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Displays sign in form
 */
public class ShowSignInFormCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        return new Routing(PagePath.LOGIN_PAGE, RoutingType.FORWARD);
    }
}
