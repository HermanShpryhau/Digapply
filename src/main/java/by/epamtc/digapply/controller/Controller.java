package by.epamtc.digapply.controller;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.Routing;
import by.epamtc.digapply.command.CommandFactory;
import by.epamtc.digapply.command.PagePath;
import by.epamtc.digapply.command.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private static void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        if (commandName == null) {
            request.getRequestDispatcher(PagePath.ERROR_404_PAGE).forward(request, response);
        }

        Command command = CommandFactory.getInstance().getCommand(commandName);
        Routing routing;
        routing = command.execute(request, response);

        String resource = routing.getResource();
        switch (routing.getRoutingType()) {
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
