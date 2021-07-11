package by.epamtc.digapply.command;

import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.resource.RequestAttribute;
import by.epamtc.digapply.resource.RequestParameter;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignUpCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Optional<String> firstName = Optional.ofNullable(request.getParameter(RequestParameter.FIRST_NAME));
        Optional<String> lastName = Optional.ofNullable(request.getParameter(RequestParameter.LAST_NAME));
        Optional<String> email = Optional.ofNullable(request.getParameter(RequestParameter.EMAIL));
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));

        // TODO Ask where to validate form data.
        UserService userService = ServiceFactory.getInstance().getUserService();
        boolean isRegistered = userService.register(
                firstName.orElse(null),
                lastName.orElse(null),
                email.orElse(null),
                password.orElse(null)
        );
        if (isRegistered) {
            return new CommandResult(Page.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
        } else {
            request.setAttribute(RequestAttribute.ERROR_ATTRIBUTE, "");
            return new CommandResult(Page.SIGNUP_PAGE, RoutingType.FORWARD);
        }
    }
}
