package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
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
