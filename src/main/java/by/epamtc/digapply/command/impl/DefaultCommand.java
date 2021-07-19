package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.CommandResult;
import by.epamtc.digapply.command.PagePath;
import by.epamtc.digapply.command.RoutingType;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return new CommandResult(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
    }
}
