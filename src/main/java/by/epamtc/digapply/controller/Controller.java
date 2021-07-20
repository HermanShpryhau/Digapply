package by.epamtc.digapply.controller;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.CommandResult;
import by.epamtc.digapply.command.CommandFactory;
import by.epamtc.digapply.dao.connection.ConnectionPool;
import by.epamtc.digapply.dao.connection.ConnectionPoolException;
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
    private static final Logger logger = LogManager.getLogger(Controller.class);

    private static void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        request.setCharacterEncoding("UTF-8");
        if (commandName == null || "".equals(commandName)) {
            request.getRequestDispatcher(PagePath.PROFILE_PAGE).forward(request, response);
        }

        Command command = CommandFactory.getInstance().getCommand(commandName);
        CommandResult commandResult;
        commandResult = command.execute(request, response);

        String page = commandResult.getPage();
        switch (commandResult.getRoutingType()) {
            case FORWARD:
                request.getRequestDispatcher(page).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + page);
                break;
            default:
                logger.error("Unknown routing type!");
                response.sendRedirect(PagePath.ERROR_PAGE);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().initialize();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }
}
