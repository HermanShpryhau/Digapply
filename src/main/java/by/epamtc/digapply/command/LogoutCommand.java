package by.epamtc.digapply.command;

import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final String LOGOUT_PAGE = "WEB-INF/jsp/logout.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.invalidate();
        return new CommandResult(LOGOUT_PAGE, CommandResultType.REDIRECT);
    }
}
