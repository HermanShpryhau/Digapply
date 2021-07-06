package by.epamtc.digapply.controller;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.CommandResult;
import by.epamtc.digapply.command.factory.CommandFactory;
import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().init();
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

    private static void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        request.setCharacterEncoding("UTF-8");
        if (commandName == null || "".equals(commandName)) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        Command command = CommandFactory.getInstance().getCommand(commandName);
        CommandResult commandResult = null;
        try {
            commandResult = command.execute(request, response);
        } catch (ServiceException e) {
            LOGGER.error("Unable to execute command.", e);
            throw new ServletException(e);
        }
        String page = commandResult.getPage();
        if (commandResult.isRedirect()) {
            response.sendRedirect(page);
        } else if (commandResult.isForward()) {
            request.getRequestDispatcher(page).forward(request, response);
        }
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
