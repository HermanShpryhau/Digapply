package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.UserService;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@DiscoverableCommand(CommandName.SIGNUP_COMMAND)
public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> firstName = Optional.ofNullable(request.getParameter(RequestParameter.FIRST_NAME));
        Optional<String> lastName = Optional.ofNullable(request.getParameter(RequestParameter.LAST_NAME));
        Optional<String> email = Optional.ofNullable(request.getParameter(RequestParameter.EMAIL));
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));

        boolean isRegistered;
        try {
            isRegistered = userService.register(
                    firstName.orElse(null),
                    lastName.orElse(null),
                    email.orElse(null),
                    password.orElse(null)
            );
        } catch (ServiceException e) {
            logger.error("Unable to register new user. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
        if (isRegistered) {
            return new Redirect(PagePath.PROFILE_PAGE_REDIRECT);
        } else {
            request.getSession().setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.INVALID_USER_DATA);
            return new Forward(PagePath.SIGNUP_PAGE_REDIRECT);
        }
    }
}
