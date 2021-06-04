package by.epamtc.digapply.controller.command;

import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {

    public ShowPageCommand() {}

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page = request.getParameter("page");
        return CommandResult.forward(page);
    }
}
