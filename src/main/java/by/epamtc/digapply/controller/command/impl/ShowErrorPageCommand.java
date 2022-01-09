package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Routing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@DiscoverableCommand(CommandName.SHOW_ERROR_PAGE_COMMAND)
public class ShowErrorPageCommand implements Command {
    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        return new Forward(PagePath.ERROR_PAGE);
    }
}
