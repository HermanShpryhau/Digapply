package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignUpCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> firstName = Optional.ofNullable(request.getParameter(RequestParameter.FIRST_NAME));
        Optional<String> lastName = Optional.ofNullable(request.getParameter(RequestParameter.LAST_NAME));
        Optional<String> email = Optional.ofNullable(request.getParameter(RequestParameter.EMAIL));
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));

        UserService userService = ServiceFactory.getInstance().getUserService();
        boolean isRegistered = false;
        try {
            isRegistered = userService.register(
                    firstName.orElse(null),
                    lastName.orElse(null),
                    email.orElse(null),
                    password.orElse(null)
            );
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_PAGE, RoutingType.FORWARD);
        }
        if (isRegistered) {
            return new CommandResult(PagePath.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
        } else {
            request.setAttribute(RequestAttribute.ERROR_ATTRIBUTE, "");
            return new CommandResult(PagePath.SIGNUP_PAGE, RoutingType.FORWARD);
        }
    }
}
