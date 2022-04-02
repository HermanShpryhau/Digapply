package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestAttribute;
import by.epamtc.digapply.entity.dto.UserDto;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@DiscoverableCommand(CommandName.SHOW_USERS_TABLE_COMMAND)
public class ShowUsersTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            List<UserDto> users = userService.retrieveAllUsersAsDto();
            request.setAttribute(RequestAttribute.USERS, users);
            return new Forward(PagePath.USER_TABLE_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to fetch all users' DTOs. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
    }
}
