package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@DiscoverableCommand(CommandName.LOGOUT_COMMAND)
public class LogoutCommand implements Command {
    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.USERNAME);
        session.removeAttribute(SessionAttribute.ROLE);
        session.removeAttribute(SessionAttribute.USER_ID);
        session.invalidate();
        return new Redirect(PagePath.HOME_PAGE_REDIRECT);
    }
}
