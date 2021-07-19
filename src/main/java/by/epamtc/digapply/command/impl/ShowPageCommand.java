package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(RequestParameter.PAGE);
        if (page != null) {
            return new CommandResult(page, RoutingType.FORWARD);
        } else {
            return new CommandResult(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
        }
    }
}
