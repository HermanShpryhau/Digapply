package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.dto.UserDto;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUsersTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            List<UserDto> users = userService.retrieveAllUsersAsDto();
            request.setAttribute(RequestAttribute.USERS, users);
            return new Routing(PagePath.USER_TABLE_PAGE, RoutingType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Unable to fetch all users' DTOs. {}", e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
