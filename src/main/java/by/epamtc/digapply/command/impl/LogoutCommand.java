package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.USERNAME);
        session.removeAttribute(SessionAttribute.ROLE);
        session.invalidate();
        return new CommandResult(PagePath.HOME_PAGE_REDIRECT, RoutingType.REDIRECT);
    }
}
