package by.epamtc.digapply.controller.command;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    public LogoutCommand() {}

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            session.invalidate();
            return CommandResult.forward("WEB-INF/jsp/logout.jsp");
        } else {
            return CommandResult.forward("WEB-INF/jsp/info.jsp");
        }

    }
}
