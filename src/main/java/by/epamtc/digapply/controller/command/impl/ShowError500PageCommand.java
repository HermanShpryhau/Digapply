package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.Command;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.Routing;
import by.epamtc.digapply.controller.command.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowError500PageCommand implements Command {
    private static final int RESPONSE_STATUS = 500;
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(RESPONSE_STATUS);
        return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
    }
}
