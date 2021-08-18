package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> firstName = Optional.ofNullable(request.getParameter(RequestParameter.FIRST_NAME));
        Optional<String> lastName = Optional.ofNullable(request.getParameter(RequestParameter.LAST_NAME));
        Optional<String> email = Optional.ofNullable(request.getParameter(RequestParameter.EMAIL));
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));

        UserService userService = ServiceFactory.getInstance().getUserService();
        boolean isRegistered;
        try {
            isRegistered = userService.register(
                    firstName.orElse(null),
                    lastName.orElse(null),
                    email.orElse(null),
                    password.orElse(null)
            );
            logger.debug("IS REGISTERED {}", isRegistered);
        } catch (ServiceException e) {
            logger.error("Unable to register new user. {}", e.getMessage());
            return Routing.ERROR;
        }
        if (isRegistered) {
            return new Routing(PagePath.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
        } else {
            request.getSession().setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.USER_EXISTS);
            return Routing.ERROR;
        }
    }
}
