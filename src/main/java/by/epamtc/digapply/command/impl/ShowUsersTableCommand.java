package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.dto.UserDto;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUsersTableCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            List<UserDto> users = userService.retrieveAllUsersAsDto();
            request.setAttribute(RequestAttribute.USERS, users);
            return new Routing(PagePath.USER_TABLE_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
    }
}
