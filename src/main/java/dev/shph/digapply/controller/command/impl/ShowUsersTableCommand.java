package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.entity.dto.UserDto;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.SHOW_USERS_TABLE_COMMAND)
public class ShowUsersTableCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
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
