package dev.shph.digapply.controller;

import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.container.CommandContainer;
import dev.shph.commandeur.routing.Routing;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Autowired
    private CommandContainer commandContainer;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        if (commandName == null) {
            request.getRequestDispatcher(PagePath.ERROR_404_PAGE).forward(request, response);
        }

        Command command = commandContainer.resolve(commandName);
        Routing routing = command.result(request, response);

        String resource = routing.uri();
        switch (routing.type()) {
            case FORWARD:
                request.getRequestDispatcher(resource).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + resource);
                break;
            default:
                request.getRequestDispatcher(PagePath.ERROR_404_PAGE).forward(request, response);
        }
    }
}
