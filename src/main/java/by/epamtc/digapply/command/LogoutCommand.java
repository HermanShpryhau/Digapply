package by.epamtc.digapply.command;

import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.invalidate();
        return new CommandResult(Page.HOME_PAGE_REDIRECT, CommandResultType.REDIRECT);
    }
}
