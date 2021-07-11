package by.epamtc.digapply.command;

import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.resource.RequestParameter;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page = request.getParameter(RequestParameter.PAGE);
        if (page != null) {
            return new CommandResult(page, RoutingType.FORWARD);
        } else {
            return new CommandResult(Page.ERROR_PAGE, RoutingType.FORWARD);
        }
    }
}
