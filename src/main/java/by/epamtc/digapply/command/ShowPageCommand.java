package by.epamtc.digapply.command;

import by.epamtc.digapply.resource.Pages;
import by.epamtc.digapply.resource.Parameters;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page = request.getParameter(Parameters.PAGE_PARAMETER);
        if (page != null) {
            return new CommandResult(page, CommandResultType.FORWARD);
        } else {
            return new CommandResult(Pages.ERROR_PAGE, CommandResultType.FORWARD);
        }
    }
}
