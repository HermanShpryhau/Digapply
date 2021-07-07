package by.epamtc.digapply.command;

import by.epamtc.digapply.resource.Pages;
import by.epamtc.digapply.resource.Parameters;
import by.epamtc.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page = request.getParameter(Parameters.PAGE_PARAMETER);
        if (page != null) {
            return new CommandResult(page, CommandResultType.REDIRECT);
        } else {
            return new CommandResult(Pages.ERROR_PAGE, CommandResultType.REDIRECT);
        }
    }
}
