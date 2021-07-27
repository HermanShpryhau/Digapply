package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.PagePath;
import by.epamtc.digapply.command.Routing;
import by.epamtc.digapply.command.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowSignUpCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        return new Routing(PagePath.SIGNUP_PAGE, RoutingType.FORWARD);
    }
}
