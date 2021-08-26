package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowErrorPageCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
    }
}
