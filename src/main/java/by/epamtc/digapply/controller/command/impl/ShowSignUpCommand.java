package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.PagePath;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Routing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@DiscoverableCommand(CommandName.SHOW_SIGNUP_COMMAND)
public class ShowSignUpCommand implements Command {
    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        return new Forward(PagePath.SIGNUP_PAGE);
    }
}
