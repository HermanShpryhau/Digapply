package by.epamtc.digapply.command;

import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return new CommandResult(Page.PROFILE_PAGE, RoutingType.FORWARD);
    }
}
