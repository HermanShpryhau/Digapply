package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component(CommandName.SHOW_SIGN_IN_COMMAND)
public class ShowSignInFormCommand implements Command {
    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        return new Forward(PagePath.LOGIN_PAGE);
    }
}
