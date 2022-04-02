package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Routing;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@DiscoverableCommand(CommandName.SHOW_ERROR_404_PAGE_COMMAND)
public class ShowError404PageCommand implements Command {
    private static final int RESPONSE_STATUS = 404;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(RESPONSE_STATUS);
        return new Forward(PagePath.ERROR_404_PAGE);
    }
}
